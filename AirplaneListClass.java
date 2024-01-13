package task;

import java.util.*;

public class AirplaneListClass
{
    private List<Airplane> airplaneslist;

    public void AirpaneCreate(){
        int i = 0;
        Airplane a=new Airplane(i++);
        airplaneslist.add(a);
    }
    public void RemoveAirplane() {
        System.out.println("Enter ID of Airplane to be Removed");
        int a;
        Scanner s = new Scanner(System.in);
        a = s.nextInt();

        for (int i = 0; i < airplaneslist.size(); i++) {
            if (airplaneslist.get(i).getId() == a) {
                airplaneslist.remove(i);
            }
        }
    }
        public void TaskSending(){
            System.out.println("Enter ID of Airplane to send the task");
            int a;
            Scanner s=new Scanner(System.in);
            a=s.nextInt();

            for (int i=0; i<airplaneslist.size(); i++){
                if (airplaneslist.get(i).getId()==a){
                    System.out.println("Sending task to Airplane with ID " +airplaneslist.get(i).getId());

                }

            }

}

}
