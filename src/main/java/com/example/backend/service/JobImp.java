package com.example.backend.service;

import com.example.backend.dao.JobDao;
import com.example.backend.dao.Job_capDao;
import com.example.backend.dao.UserDao;
import com.example.backend.dao.User_JobDao;
import com.example.backend.entity.Job;
import com.example.backend.entity.Job_cap;
import com.example.backend.entity.User;
import com.example.backend.entity.UserJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class JobImp implements JobService{
    @Autowired
    private JobDao jobDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private Job_capDao job_capDao;
    @Autowired
    private User_JobDao user_jobDao;
    @Override
    public Map<String, Object> getJobByName(String job_name) {

        Map<String, Object> map = new TreeMap<>();
        try{
            List<Job> jobs = jobDao.findByJobName(job_name);
            map.put("state", 1);
            map.put("total", jobs.size());
            map.put("data", jobs);

        } catch (Exception e){
            map.put("state", 0);
        }
        return map;
    }

    @Override
    public Map<String, Object> getJobSalByName(String job_name) {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<Job> jobs = jobDao.findByJobName(job_name);

            List<Double> sal = new ArrayList<>();
            for (Job s:jobs){
                if (s.getJobSal() != 0) {
                    sal.add(s.getJobSal());
                }
            }
            map.put("state", 1);
            map.put("data", sal);

        } catch (Exception e){
            map.put("state", 0);
        }

        return map;
    }

    @Override
    public Map<String, Object> getJobSalNum(String job_name, double sal1, double sal2) {
        Map<String, Object> map = new TreeMap<>();
        try{
            List<Job> jobs = jobDao.findByJobNameAndJobSalGreaterThanEqualAndJobSalLessThan(job_name, sal1, sal2);

            map.put("state", 1);
            map.put("data", jobs.size());

        } catch (Exception e){
            map.put("state", 0);
        }

        return map;
    }

    @Override
    public Map<String, Object> getJobByTitle(String job_title, Pageable pageable) {

        Map<String, Object> maps = new HashMap<>();
        try{
            Page<Job> jobs = jobDao.findByJobTitleLike(job_title, pageable);
            maps.put("state", 1);
            maps.put("data", jobs);
            maps.put("total", jobs.getTotalElements());
        } catch (Exception e){
            maps.put("state", 0);
        }
        return maps;
    }

    @Override
    public Map<String, Object> getJobs(String user_id) {
        Map<String, Object> maps = new HashMap<>();
        Map<String, Integer> grade = new HashMap<>();
        grade.put("无", 0);
        grade.put("初中", 1);
        grade.put("高中", 2);
        grade.put("大专", 3);
        grade.put("本科", 4);
        grade.put("硕士", 5);
        grade.put("博士", 6);
        try{
            User user = userDao.getUserById(user_id);
            String[] caps = user.getUser_caps();
            List<Job_cap> job_caps = new ArrayList<>();
            Map<String, Double> map = new HashMap<>();
            for (String cap:caps){
                job_caps.addAll(job_capDao.getJobs(cap));
            }
            for (Job_cap job_cap:job_caps){
                if (map.containsKey(job_cap.getJob_name())){
                    map.put(job_cap.getJob_name(),map.get(job_cap.getJob_name()) + Double.parseDouble(job_cap.getSupport()));
                }
                else {
                    map.put(job_cap.getJob_name(), Double.parseDouble(job_cap.getSupport()));
                }
            }

            Set<Map.Entry<String, Double>> set = map.entrySet();
            List<Map.Entry<String, Double>> list = new ArrayList<Map.Entry<String, Double>>(set);
            list.sort(new Comparator<Map.Entry<String, Double>>() {
                @Override
                public int compare(Map.Entry<String, Double> stringIntegerEntry, Map.Entry<String, Double> t1) {
                    if (t1.getValue() - stringIntegerEntry.getValue() > 0){
                        return 1;
                    }
                    else if (t1.getValue() - stringIntegerEntry.getValue() < 0){
                        return -1;
                    }
                    return 0;
                }
            });
            List<Job> jobs = new ArrayList<>();
            List<String> job_map = new ArrayList<>();
            for (Map.Entry<String, Double> job:list){
                job_map.add(job.getKey());
                List<Job> jobs1 = new ArrayList<>();
                try {
                    jobs1 = jobDao.findByJobName(job.getKey());
                }catch (Exception e){
                    continue;
                }
                for (Job job1:jobs1){
                    String exp = job1.getJobExp();
                    int exp_num = 0;
                    if (exp.equals("不限") || exp.equals("无经验")){
                        exp_num = 0;
                    }
                    else if (exp.contains("以下")){
                        try {
                            exp_num = Integer.parseInt(exp.substring(0, 1));
                        }catch (Exception e){
                            continue;
                        }
                    }
                    else if(exp.contains("-")){
                        try {
                            exp = exp.replace("年", "");
                            String[] strings = exp.split("-");
                            exp_num = Integer.parseInt(strings[1]);
                        }catch (Exception e){
                            continue;
                        }
                    }
                    String salary = job1.getJobSalary();
                    double money = 0;
                    if (salary.contains("面议")){
                        continue;
                    }
                    else if(salary.contains("-") &&
                            (salary.charAt(salary.length() - 1) == '万' ||
                                    salary.charAt(salary.length() - 1) == '千')){
                        try {
                            if (salary.charAt(salary.length() - 1) == '万') {
                                String str = salary.substring(salary.indexOf('-') + 1, salary.length() - 1);
                                double num = Double.parseDouble(str);
                                money = num * 10000;
                            } else if (salary.charAt(salary.length() - 1) == '千') {
                                String str = salary.substring(salary.indexOf('-'));
                                int num = Integer.parseInt(str);
                                money = num * 1000;
                            }
                        }catch (Exception e){
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                    if (
                            job1.getJobEdu().equals(user.getUser_edu())&&
                            job1.getJobCity().contains(user.getUser_pos())&&
                            exp_num <= user.getUser_exp() && money >= user.getUser_minsal()
//                            && (grade.containsKey(job1.getJob_edu())
//                                    && grade.get(job1.getJob_edu()) < grade.get(user.getUser_edu()))
                    ){
                        jobs.add(job1);
                    }
                }
            }
            maps.put("state", 1);
            if (jobs.size() >= 10) {
                maps.put("total", 10);
                maps.put("data", jobs.subList(0, 10));
            }
            else{
                maps.put("total", jobs.size());
                maps.put("data", jobs);
            }
            if (job_map.size() >= 3){
                maps.put("jobs", job_map.subList(0, 3));
            }
            else{
                maps.put("jobs", job_map);
            }
        }
        catch (Exception e){
            maps.put("state", 0);
            maps.put("msg", "请完善简历信息");
        }
        return maps;
    }

    @Override
    public Map<String, Object> getJobsByRan() {
        Map<String, Object> maps = new HashMap<>();
        try{
            List<Job> jobs = jobDao.randomFindJobs();
            maps.put("state", 1);
            maps.put("data", jobs);
        } catch (Exception e){
            maps.put("state", 0);
        }
        return maps;
    }

    @Override
    public Map<String, Object> getJobByNo(int job_no) {
        Map<String, Object> maps = new HashMap<>();
        try{
            Job job = jobDao.findByJobNo(job_no);
            maps.put("state", 1);
            maps.put("data", job);
        } catch (Exception e){
            maps.put("state", 0);
        }
        return maps;
    }

    @Override
    public Map<String, Object> getSalByCity() {
        Map<String, Object> maps = new HashMap<>();
        String[] strings = {"上海", "北京", "广州", "深圳", "天津", "武汉", "西安", "成都", "南京", "杭州", "重庆", "厦门"};
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        try{
            List<Double> sal = new ArrayList<>();
            for (String str : list){
                double pre_sal = 0.0;
                List<Job> jobs = jobDao.findByJobCityLike(str);
                for (Job job:jobs){
                    double s = job.getJobSal();
                    if (s != 0){
                        pre_sal += s;
                    }
                }
                sal.add(pre_sal / jobs.size());
            }
            maps.put("state", 1);
            maps.put("data", sal);
        } catch (Exception e){
            maps.put("state", 0);
        }
        return maps;
    }

    @Override
    public Map<String, Object> getSalByName() {
        Map<String, Object> maps = new HashMap<>();
        String[] strings = {"数据分析师", "Java开发", "Web前端", "算法工程师", "Python", "UI设计师", "Android", "深度学习", "人工智能", "PHP",
                "Hadoop", "数据开发", "电气工程师", "电子工程师", "PLC", "测试工程师", "设备工程师", "硬件工程师", "结构工程师", "硬件工程师",
                "投资经理", "风控催收", "银行柜员", "银行销售", "信审", "信用卡", "贷款", "金融产品", "汽车金融", "金融研究", "证券交易员",
                "投资经理", "期货操盘手", "基金", "股票", "投资顾问", "信托", "典当", "担保", "信贷", "权证", "财产保险", "保险内勤","理赔精算师",
                "保险销售", "理财顾问", "查勘定损", "车险", "教师", "英语老师", "课程顾问", "教务", "美术老师", "幼教", "小学教师",
                "班主任助教", "编导", "摄影师", "编剧", "摄影", "后期制作", "制片", "记者", "剪辑", "化妆师", "广告创意", "美术指导", "策划经理",
                "文案", "广告制作", "媒介", "广告审核", "平面设计", "网页设计", "插画师", "工业设计", "视觉设计"};
        List<String> list = new ArrayList<>(Arrays.asList(strings));
        try{
            List<Double> sal = new ArrayList<>();
            for (String str : list){
                double pre_sal = 0.0;
                List<Job> jobs = jobDao.findByJobName(str);
                for (Job job:jobs){
                    double s = job.getJobSal();
                    if (s != 0){
                        pre_sal += s;
                    }
                }
                sal.add(pre_sal / jobs.size());
            }
            maps.put("state", 1);
            maps.put("data", sal);
        } catch (Exception e){
            maps.put("state", 0);
        }
        return maps;
    }

    @Override
    public Map<String, Object> getRecommend(String current_user){
        List<Integer> re_jobs = new ArrayList<>();
        List<Job> jobs = new ArrayList<>();
        Map<String, Object> maps = new HashMap<>();
        //查找用户总量
        List<UserJob> userJobs =  user_jobDao.findAll();
        Set<String> user_id = new HashSet<>();
        for (UserJob userJob : userJobs){
            user_id.add(userJob.getUserId());
        }
        List<String> users = new ArrayList<>(user_id);
        int N = users.size();
        int[][] sparseMatrix = new int[N][N];
        //建立用户稀疏矩阵，用于用户相似度计算【相似度矩阵】
        Map<String, Integer> userItemLength = new HashMap<>();
        //存储每一个用户对应的不同物品总数
        Map<String, Set<String>> itemUserCollection = new HashMap<>();
        //建立物品到用户的倒排表
        Set<String> items = new HashSet<>();
        //辅助存储物品集合
        Map<String, Integer> userID = new HashMap<>();
        //辅助存储每一个用户的用户ID映射
        Map<Integer, String> idUser = new HashMap<>();
        //辅助存储每一个ID对应的用户映射

        for (int i = 0; i < N ; i++){
            //依次处理N个用户 输入数据 以空格间隔
            List<UserJob> list = user_jobDao.findByUserId(users.get(i));

            List<String> job_names1 = new ArrayList<>();
            for (UserJob userJob:list){
                job_names1.add(userJob.getJobId()+"");
            }
            System.out.println(job_names1);

            String[] user_item = new String[job_names1.size() + 1];
            user_item[0] = users.get(i);
            for (int j = 1  ; j < job_names1.size(); j++){
                user_item[j] = job_names1.get(j);
            }

            int length = user_item.length;
            userItemLength.put(user_item[0], length-1);
            //eg: A 3
            userID.put(user_item[0], i);

            //用户ID与稀疏矩阵建立对应关系
            idUser.put(i, user_item[0]);
            //建立物品--用户倒排表
            for (int j = 1; j < length; j ++){
                if(items.contains(user_item[j])){
                    //如果已经包含对应的物品--用户映射，直接添加对应的用户
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                } else{
                    //否则创建对应物品--用户集合映射
                    items.add(user_item[j]);
                    itemUserCollection.put(user_item[j], new HashSet<String>());
                    //创建物品--用户倒排关系
                    itemUserCollection.get(user_item[j]).add(user_item[0]);
                }
            }
        }
        System.out.println(itemUserCollection.toString());
        //计算相似度矩阵【稀疏】
        Set<Map.Entry<String, Set<String>>> entrySet = itemUserCollection.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Set<String> commonUsers = iterator.next().getValue();
            for (String user_u : commonUsers) {
                for (String user_v : commonUsers) {
                    if(user_u.equals(user_v)){
                        continue;
                    }
                    sparseMatrix[userID.get(user_u)][userID.get(user_v)] += 1;
                    //计算用户u与用户v都有正反馈的物品总数
                }
            }
        }
        System.out.println(userItemLength.toString());
        System.out.println(userID.get(current_user));
        //计算用户之间的相似度【余弦相似性】
        int recommendUserId = userID.get(current_user);
        for (int j = 0;j < sparseMatrix.length; j++) {
            if(j != recommendUserId){
                System.out.println(idUser.get(recommendUserId)+"--"+idUser.get(j)+"相似度:"+sparseMatrix[recommendUserId][j]/Math.sqrt(userItemLength.get(idUser.get(recommendUserId))*userItemLength.get(idUser.get(j))));
            }
        }
        //计算指定用户recommendUser的物品推荐度
        for (String item: items){
            //遍历每一件物品
            Set<String> users1 = itemUserCollection.get(item);
            //得到购买当前物品的所有用户集合
            if(!users1.contains(current_user)){
                //如果被推荐用户没有购买当前物品，则进行推荐度计算
                double itemRecommendDegree = 0.0;
                for (String user: users1){
                    itemRecommendDegree += sparseMatrix[userID.get(current_user)][userID.get(user)]/Math.sqrt(userItemLength.get(current_user)*userItemLength.get(user));
                    //推荐度计算
                }
                System.out.println("The item "+item+" for "+ current_user +"'s recommended degree:"+itemRecommendDegree);
                re_jobs.add(Integer.parseInt(item));
            }
        }
        Set<String> job_names = new HashSet<>();
        for (Integer item:re_jobs){
            try{
                Job job = jobDao.findByJobNo(item);
                maps.put("state", 1);
                jobs.add(job);
                job_names.add(job.getJobName());
            } catch (Exception e){
                maps.put("state", 0);
            }
        }
        maps.put("data", jobs);
        maps.put("jobs", job_names);
        return maps;
    }
}
