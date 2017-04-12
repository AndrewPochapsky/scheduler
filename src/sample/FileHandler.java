package sample;

import java.io.*;

public class FileHandler {

    public static void save(Scheduler scheduler) throws IOException{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("schedulers/"+scheduler.getTitle()+".bin"));
        objectOutputStream.writeObject(scheduler);
        System.out.println("Successfully saved!");
    }

    public static void load(String title)throws IOException, ClassNotFoundException{
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("schedulers/"+title+".bin"));
        Scheduler scheduler= (Scheduler) objectInputStream.readObject();
        ProgramController.setCurrentScheduler(scheduler);
        System.out.println("Loading file: "+title+".bin");
    }



}
