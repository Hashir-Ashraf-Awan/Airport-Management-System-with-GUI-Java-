package task;

import java.util.*;
class TaskQueue  {

     protected PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

   public void comparePriorityAndTime(ArrayList<Task> tasks) {
      for (int i = 1; i < tasks.size(); i++) {
         if (tasks.get(i).getPriority() > tasks.get(0).getPriority()) {
            System.out.println(tasks.get(i).getTaskName() + " has higher priority than " + tasks.get(0).getTaskName());
            swapTasks(tasks, i, 0);
         } else if (tasks.get(i).getPriority() < tasks.get(0).getPriority()) {
            System.out.println(tasks.get(0).getTaskName() + " has higher priority than " + tasks.get(i).getTaskName());
            swapTasks(tasks, i, 0);
         } else {
            if (tasks.get(i).getTimeMark() < tasks.get(0).getTimeMark()) {
               System.out.println(tasks.get(i).getTaskName() + " has higher priority and earlier time than " + tasks.get(0).getTaskName());
               swapTasks(tasks, i, 0);
            } else if (tasks.get(i).getTimeMark() > tasks.get(0).getTimeMark()) {
               System.out.println(tasks.get(0).getTaskName() + " has higher priority and earlier time than " + tasks.get(i).getTaskName());
            } else {
               System.out.println("Both tasks have the same priority and time.");
            }
         }
      }
   }

   private void swapTasks(ArrayList<Task> tasks, int index1, int index2) {
      Task temp = tasks.get(index1);
      tasks.set(index1, tasks.get(index2));
      tasks.set(index2, temp);
   }


   public void printPriorityQueue() {
      System.out.println("Tasks in Priority Queue:");
      while (!priorityQueue.isEmpty()) {
         System.out.println(priorityQueue.poll());
      }
   }
}