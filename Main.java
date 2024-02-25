import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        Binary_Heap<Task> taskHeap = null;
        String text;
        int p;

        boolean run = true;
        do{
            System.out.println("Please select from below:\n1 - add list of Tasks\n2 - add single Task\n3 - show tasks\n4 - show most important task\n5 - delete most important\n6 - sort tasks\n7 - close program\n\t\t..... ");
            int choice = scanner.nextInt();

//            System.out.print("\033[H\033[2J");
//            System.out.flush();

            switch(choice){
                case 1:
                    System.out.println("How many tasks: ");
                    int num = scanner.nextInt();
                    scanner.nextLine(); //to avoid the new_line in input buffer
                    var taskArr = new ArrayList<Task>();

                    for(int i = 0; i < num; i++){
                        System.out.println("Enter task text: ");
                        text = scanner.nextLine();
                        System.out.println("Enter priority value: ");
                        p = scanner.nextInt();
                        scanner.nextLine(); //to avoid the new_line in input buffer
                        var task = new Task(text, p);
                        taskArr.add(task);
                    }

                    taskHeap = new Binary_Heap<Task>(taskArr, Task.task_cmp);

                    break;
                case 2:
                    scanner.nextLine(); //to avoid the new_line in input buffer

                    System.out.println("Enter task text: ");
                    text = scanner.nextLine();
                    System.out.println("Enter priority value: ");
                    p = scanner.nextInt();

                    scanner.nextLine(); //to avoid the new_line in input buffer

                    var one_task = new Task(text, p);

                    if(taskHeap == null){
                        taskHeap = new Binary_Heap<Task>(one_task, Task.task_cmp);
                        break;
                    }

                    taskHeap.add_to_heap(one_task, Task.task_cmp);

                    break;
                case 3:
                    System.out.println("Task in current queue: \n");

                    if(taskHeap == null){
                        System.out.println("Queue is empty\n");
                        break;
                    }

                    for(var task : taskHeap.get_heap())
                        System.out.println("Priority: " + task.priority + " - " + task.content + "\n");

                    break;
                case 4:
                    if(taskHeap == null){
                        System.out.println("Queue is empty\n");
                        break;
                    }

                    System.out.println("Most important task is: \n");
                    System.out.println("Priority: " + taskHeap.get_heap().get(0).priority + " - " + taskHeap.get_heap().get(0).content);

                    break;
                case 5:
                    if(taskHeap == null){
                        System.out.println("Queue is empty\n");
                        break;
                    }

                    taskHeap.remove_greatest(Task.task_cmp);
                    break;
                case 6:
                    if(taskHeap == null){
                        System.out.println("Queue is empty\n");
                        break;
                    }

                    taskHeap.heap_sort(Task.task_cmp);
                    System.out.println("Sorted tasks: ");

                    for(var task : taskHeap.get_heap())
                        System.out.println("Priority: " + task.priority + " - " + task.content);

                    break;
                case 7:
                    run = false;

                    if(taskHeap == null)
                        break;

                    try{
                        FileWriter file_tasks = new FileWriter("tasks.txt");
                        for(var x : taskHeap.get_heap()){
                            String to_write = "Priority: " + x.priority + " - " + x.content + "\n";
                            file_tasks.write(to_write);
                        }
                        file_tasks.close();
                    }
                    catch(IOException e){
                        System.out.println("Error on saving to file");
                        e.printStackTrace();
                    }

                    break;
            }
        }while(run);
    }
}

class Task{
    String content;
    int priority;

    public Task(String c, int p){
        this.content = c;
        this.priority = p;
    }

    public static Comparator<Task> task_cmp = Comparator.comparingInt(task -> task.priority);
}