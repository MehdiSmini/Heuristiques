import java.util.Arrays;

public class Job {
    private Integer nbTasks ;

    @Override
    public String toString() {
        return "Job{" +
                "nbTasks=" + nbTasks +
                ", Tasks=" + Arrays.toString(Tasks) +
                '}';
    }

    private Integer id ;

    public Integer getId() {
        return id;
    }

    private Task Tasks[];
    private Integer indexLastTask = 0;

    public Job(Integer nbTasks, Integer id){
        this.nbTasks = nbTasks ;
        this.id = id ;
        this.Tasks = new Task[this.nbTasks];
    }

    public void addTask(Task task){
        if(indexLastTask < nbTasks){
            Tasks[indexLastTask] = task ;
            indexLastTask++;
        } else {
            System.out.println("Error addTask : too many tasks");
        }
    }

    public Integer getNbTasks() {
        return nbTasks;
    }

    public Task[] getTasks() {
        return Tasks;
    }

    public Task getTask(Integer i){ return Tasks[i];}
}
