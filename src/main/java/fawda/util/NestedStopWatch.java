package fawda.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;
import org.springframework.util.StringUtils;

import com.risen.base.util.MyUtils;

public class NestedStopWatch {
    
    private Stack<NestWatch> stopWatchs = new Stack<NestWatch>();
    
    private NestWatch currentWatch;
    
    public NestedStopWatch() {
        this("");
    }
    
    public NestedStopWatch(String id) {
        currentWatch = new NestWatch(null, new StopWatch(id));
    }
    
    public TaskInfo getLastTaskInfo() throws IllegalStateException {
        return currentWatch.stopWatch.getLastTaskInfo();
    }
    
    public String getLastTaskName() throws IllegalStateException {
        return currentWatch.stopWatch.getLastTaskName();
    }
    
    public long getLastTaskTimeMillis() throws IllegalStateException {
        return currentWatch.stopWatch.getLastTaskTimeMillis();
    }
    
    public int getTaskCount() {
        return currentWatch.stopWatch.getTaskCount();
    }
    
    public long getTotalTimeMillis() {
        return currentWatch.stopWatch.getTotalTimeMillis();
    }
    
    public double getTotalTimeSeconds() {
        return currentWatch.stopWatch.getTotalTimeSeconds();
    }
    
    public boolean isRunning() {
        return currentWatch.stopWatch.isRunning();
    }
    
    public String prettyPrint() {
        StringBuilder sb = new StringBuilder(shortSummary());
        sb.append('\n');
        sb.append("-----------------------------------------\n");
        sb.append("任务名称          消耗占比(%)      消耗时间(ms)\n");
        sb.append("-----------------------------------------\n");
        
        //结束可能没有完成的任务
        while (this.stopWatchs.size() > 0) {
            this.stop();
        }
        
        List<NestWatch> children = Arrays.asList(this.currentWatch);
        this.prettyPrint(children, sb, this.getTotalTimeSeconds(), "");
        return sb.toString();
    }
    
    public void prettyPrint(List<NestWatch> nestWatchs, StringBuilder sb, double totalTime, String indent) {
        for (NestWatch nestWatch : nestWatchs) {
            TaskInfo[] taskInfo = nestWatch.stopWatch.getTaskInfo();
            List<NestWatch> children = nestWatch.children;
            Map<String, List<NestWatch>> groupByParentTaskName = MyUtils.GroupBy(children, "parentTaskName");
            double sum = 0;
            for (TaskInfo task : taskInfo) {
                sum += task.getTimeSeconds();
                sb.append(indent);
                sb.append(task.getTaskName()).append("    ");
                sb.append(Math.round((task.getTimeSeconds() / totalTime) * 10000) / 100.0).append("%    ");
                sb.append(task.getTimeMillis()).append("\n");
                if (groupByParentTaskName.containsKey(task.getTaskName())) {
                    List<NestWatch> childrenNestWatchs = groupByParentTaskName.get(task.getTaskName());
                    this.prettyPrint(childrenNestWatchs, sb, task.getTimeSeconds(), indent + "    ");
                }
            }
            double otherRate = Math.round(((totalTime - sum) / totalTime) * 10000) / 100.0;
            if (otherRate > 3) {
                sb.append(indent);
                sb.append("其余消耗").append("====>");
                sb.append(otherRate).append("%    ");
                sb.append(Math.round((totalTime - sum) * 1000)).append("\n");
            }
        }
    }
    
    public void setKeepTaskList(boolean keepTaskList) {
        currentWatch.stopWatch.setKeepTaskList(keepTaskList);
    }
    
    public String shortSummary() {
        return currentWatch.stopWatch.shortSummary();
    }
    
    public void start() throws IllegalStateException {
        this.start("");
    }
    
    public void start(String taskName) throws IllegalStateException {
        if (currentWatch.stopWatch.isRunning()) { //需要嵌套的watch
            this.stopWatchs.push(this.currentWatch); //暂时入栈
            this.currentWatch = new NestWatch(this.currentWatch, new StopWatch());
        }
        currentWatch.stopWatch.start(taskName);
    }
    
    public void stop() throws IllegalStateException {
        if (!this.currentWatch.stopWatch.isRunning()) {
            this.currentWatch = this.stopWatchs.pop(); //出栈
            this.stop(); //递归调用
            return;
        }
        currentWatch.stopWatch.stop();
        List<NestWatch> children = currentWatch.children;
        for (NestWatch nestWatch : children) {
            if (!StringUtils.hasText(nestWatch.parentTaskName)) { //设置上级任务名称
                nestWatch.parentTaskName = currentWatch.stopWatch.getLastTaskName();
            }
        }
    }
    
    public boolean equals(Object obj) {
        return currentWatch.stopWatch.equals(obj);
    }
    
    public int hashCode() {
        return currentWatch.stopWatch.hashCode();
    }
    
    public String toString() {
        return currentWatch.stopWatch.toString();
    }
    
    class NestWatch {
        NestWatch parent;
        
        List<NestWatch> children = new LinkedList<NestWatch>();
        
        StopWatch stopWatch;
        
        String parentTaskName;
        
        NestWatch(NestWatch parent, StopWatch stopWatch) {
            this.parent = parent;
            this.stopWatch = stopWatch;
            if (this.parent != null) {
                this.parent.children.add(this);
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        
        NestedStopWatch watch = new NestedStopWatch("测试");
        
        watch.start("任务1");
        watch.start("任务11");
        watch.start("任务111");
        Thread.sleep(random.nextInt(100));
        Thread.sleep(random.nextInt(100));
        watch.stop();
        watch.start("任务112");
        Thread.sleep(random.nextInt(100));
        watch.stop();
        Thread.sleep(random.nextInt(100));
        watch.stop();
        
        watch.start("任务12");
        Thread.sleep(random.nextInt(100));
        watch.stop();
        
        watch.start("任务13");
        Thread.sleep(random.nextInt(100));
        watch.stop();
        watch.stop();
        
        
        watch.start("任务2");
        Thread.sleep(random.nextInt(100));
        Thread.sleep(random.nextInt(100));
        Thread.sleep(random.nextInt(100));
        watch.stop();
        
        
        watch.start("任务3");
        Thread.sleep(random.nextInt(100));
        Thread.sleep(random.nextInt(100));
        Thread.sleep(random.nextInt(100));
        watch.stop();
        
        System.out.println(watch.prettyPrint());
    }
}
