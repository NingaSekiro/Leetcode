package leetcode.editor.cn;

import java.util.*;

public class TaskSelection {
    
    /**
     * 任务类，包含分数和难度
     */
    static class Task {
        int score;   // 分数
        int difficulty; // 难度
        
        Task(int score, int difficulty) {
            this.score = score;
            this.difficulty = difficulty;
        }
        
        @Override
        public String toString() {
            return "(" + score + "," + difficulty + ")";
        }
    }
    
    /**
     * 找到满足条件的最优窗口
     * @param tasks 任务数组
     * @param T 最小总分数要求
     * @return 最优窗口信息
     */
    public static Result findOptimalWindow(Task[] tasks, int T) {
        int n = tasks.length;
        if (n == 0) return null;
        
        // 使用双指针维护滑动窗口
        int left = 0;
        long sum = 0; // 窗口内分数总和
        
        // 最优解信息
        int minMaxDifficulty = Integer.MAX_VALUE;
        int bestLeft = -1, bestRight = -1;
        int windowSize = Integer.MAX_VALUE;
        
        // 单调递减双端队列，维护当前窗口中难度的最大值
        Deque<Integer> deque = new ArrayDeque<>();
        
        for (int right = 0; right < n; right++) {
            // 将右边界任务加入窗口
            sum += tasks[right].score;
            
            // 维护单调递减队列
            // 从队尾移除所有难度小于当前任务的元素
            while (!deque.isEmpty() && tasks[deque.peekLast()].difficulty <= tasks[right].difficulty) {
                deque.pollLast();
            }
            deque.offerLast(right);
            
            // 当窗口内分数总和满足条件时，尝试收缩左边界
            while (sum >= T) {
                // 获取当前窗口中的最大难度值
                // 从队头移除超出窗口范围的元素
                while (!deque.isEmpty() && deque.peekFirst() < left) {
                    deque.pollFirst();
                }
                
                int maxDifficulty = tasks[deque.peekFirst()].difficulty;
                
                // 更新最优解
                int currentWindowSize = right - left + 1;
                if (currentWindowSize < windowSize || 
                    (currentWindowSize == windowSize && maxDifficulty < minMaxDifficulty)) {
                    windowSize = currentWindowSize;
                    minMaxDifficulty = maxDifficulty;
                    bestLeft = left;
                    bestRight = right;
                }
                
                // 收缩左边界
                sum -= tasks[left].score;
                left++;
            }
        }
        
        if (bestLeft == -1) return null; // 没有满足条件的窗口
        
        return new Result(bestLeft, bestRight, windowSize, minMaxDifficulty);
    }
    
    /**
     * 结果类
     */
    static class Result {
        int leftIndex;
        int rightIndex;
        int windowSize;
        int maxDifficulty;
        
        Result(int leftIndex, int rightIndex, int windowSize, int maxDifficulty) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.windowSize = windowSize;
            this.maxDifficulty = maxDifficulty;
        }
        
        @Override
        public String toString() {
            return String.format("最优窗口: [%d, %d], 窗口大小: %d, 最大难度: %d", 
                               leftIndex, rightIndex, windowSize, maxDifficulty);
        }
    }
    
    public static void main(String[] args) {
        // 示例数据
        Task[] tasks = {
            new Task(5, 3),
            new Task(1, 2),
            new Task(3, 5),
            new Task(4, 1),
            new Task(2, 4),
            new Task(6, 2)
        };
        
        int T = 10; // 最小总分数要求
        
        System.out.println("任务列表:");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println((i + 1) + ": " + tasks[i]);
        }
        System.out.println("最小总分数要求 T: " + T);
        
        Result result = findOptimalWindow(tasks, T);
        
        if (result != null) {
            System.out.println("\n" + result);
            
            // 输出最优窗口中的任务
            System.out.println("\n最优窗口中的任务:");
            for (int i = result.leftIndex; i <= result.rightIndex; i++) {
                System.out.println((i + 1) + ": " + tasks[i]);
            }
            
            // 计算并验证窗口总分数
            long totalScore = 0;
            for (int i = result.leftIndex; i <= result.rightIndex; i++) {
                totalScore += tasks[i].score;
            }
            System.out.println("窗口总分数: " + totalScore);
        } else {
            System.out.println("没有满足条件的窗口");
        }
    }
}