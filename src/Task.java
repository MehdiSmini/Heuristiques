import java.util.Arrays;

public class Task {
    private Integer nbMachines ;

    @Override
    public String toString() {
        return "Task{" +
                "nbMachines=" + nbMachines +
                "selectedProcess=[" + selectedProcess[0] + "," + selectedProcess[1] + "]" +
                '}';
    }

    private Integer process[][] ;
    private Integer selectedProcess[] = new Integer[2];
    private Integer indexLastMachine = 0;
    private Integer startTime = 0;
    private Integer id ;

    public Integer getId() {
        return id;
    }

    public Integer[] getProcess(int index){
        return process[index];
    }
    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getIndexLastMachine() {
        return indexLastMachine;
    }

    public void setIndexLastMachine(Integer indexLastMachine) {
        this.indexLastMachine = indexLastMachine;
    }

    public Task(Integer nbMachines, Integer id){
        this.nbMachines = nbMachines;
        this.id = id ;

        this.process = new Integer[nbMachines][2];
    }

    public void addProcess(Integer machine, Integer time){
        if(indexLastMachine < nbMachines) {
            process[indexLastMachine][0] = machine;
            process[indexLastMachine][1] = time;
            indexLastMachine++;
        } else {
            System.out.println("addProcess Error : too many machines");
        }
    }

    public Integer getNbMachines() {
        return nbMachines;
    }

    public Integer[] getSelectedProcess() {
        return selectedProcess;
    }

    public void selectBest() {
        Integer tMin = Integer.MAX_VALUE ;
        Integer Min[] = new Integer[2];
        for(int i = 0; i < nbMachines ; i++){
            if(Main.machines[process[i][0]-1] < tMin) {
                tMin = Main.machines[process[i][0]-1] ;
                Min = process[i];
            }
        }
        Main.machines[Min[0]-1] += Min[1];
        selectedProcess = Min ;
    }
}
