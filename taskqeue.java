package task;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
class TaskQueue  {

   protected PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

   public void comparePriorityAndTime(ArrayList<Task> tasks) {
      for (int i = 1; i < tasks.size(); i++) {
         if (tasks.get(i).getPriority() > tasks.get(0).getPriority()) {
            System.out.println(tasks.get(i) + " has higher priority than " + tasks.get(0));
            swapTasks(tasks, i, 0);
         } else if (tasks.get(i).getPriority() < tasks.get(0).getPriority()) {
            System.out.println(tasks.get(0) + " has higher priority than " + tasks.get(i));
            swapTasks(tasks, i, 0);
         } else {
            // Prioritize based on time if priorities are the same
            if (tasks.get(i).getTimeMark() < tasks.get(0).getTimeMark()) {
               System.out.println(tasks.get(i) + " has higher priority and earlier time than " + tasks.get(0));
               swapTasks(tasks, i, 0);
            } else if (tasks.get(i).getTimeMark() > tasks.get(0).getTimeMark()) {
               System.out.println(tasks.get(0) + " has higher priority and earlier time than " + tasks.get(i));
               // No need to swap if the current task has higher priority but later time
            } else {
               System.out.println("Both tasks have the same priority and time.");
            }
         }
      }
   }

   private void swapTasks(ArrayList<Task> tasks, int index1, int index2) {
      // Swap tasks[index1] and tasks[index2] using a temporary variable
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