package task;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Scanner;
class TaskQueue  {

   protected PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();

   public void comparePriority(ArrayList<Task> tasks) {
      for (int i = 1; i < 10; i++) {
         if (tasks.get(i).getPriority() > tasks.get(0).getPriority()) {
            System.out.println(tasks.get(i) + " has higher priority than " + tasks.get(0));
            // Swap tasks[i] and tasks[0] using a temporary variable
            Task temp = tasks.get(i);
            tasks.set(i, tasks.get(0));
            tasks.set(0, temp);
         } else if (tasks.get(i).getPriority() < tasks.get(0).getPriority()) {
            System.out.println(tasks.get(0) + " has higher priority than " + tasks.get(i));
            // Swap tasks[i] and tasks[0] using a temporary variable
            Task temp = tasks.get(i);
            tasks.set(i, tasks.get(0));
            tasks.set(0, temp);

         } else {
            System.out.println("Both tasks have the same priority.");
         }
      }
   }

   public void printPriorityQueue() {
      System.out.println("Tasks in Priority Queue:");
      while (!priorityQueue.isEmpty()) {
         System.out.println(priorityQueue.poll());
      }
   }
}