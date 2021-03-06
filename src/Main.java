import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.jfree.ui.RefineryUtilities;

public class Main {

    private static Integer nJobs  = 0;
    private static Integer nMachines  = 0;
    private static Integer nTasks  = 0;
    private static Integer avgMachinesperJob;
    private static Integer machinesTime[];
    public static Integer machines[];
    public static final  int smaple_nummber=10;   //nummber of smaple
    public static int sample_array[] =new int[smaple_nummber];
    public static Integer nbMaxTasksperJob = 0;
    private static Integer maxPopulation = 0;
    private static Integer nbPopulationInitiale = 0 ;
    private static Job jobs[] ;
    private static Integer selection[];
    private static final int P = 5;
    //private static Integer OS[];
    protected static ArrayList<Integer[]> OSpopulation = new ArrayList<>();                        // list P elements de OS
    protected static ArrayList<Integer[]> MApopulation = new ArrayList<>();                        // list P elements de OS
    private static int [] population_selection= new int[2];
   // private static   ArrayList<Integer[]> sample = new ArrayList<>();
    // list P elements de MA
    //private static Integer MA[];
    private static Integer timeResult = 0;

    public static void main(String[] args)


    {



            readFile("./TextData/Monaldo/Fjsp/Job_Data/Barnes/Text/mt10c1.fjs");
        //readFile("test.fjs");

        for (int sample_counter = 0; sample_counter <smaple_nummber; sample_counter++) {   // To generate 15 sample for the result

            MApopulation.clear();
            OSpopulation.clear();

            // Intialisation de la population
        selectMachine();
        MApopulation.add(buildMA());
        solveconflict();
        OSpopulation.add(buildOS());

        nbPopulationInitiale = (nTasks * nTasks) / 2;
        maxPopulation = nbPopulationInitiale + nbPopulationInitiale / 2;

            for (int i = 0; i < nbPopulationInitiale - 1; i++) {
                mutation(0);
            }
            long currentTime = System.currentTimeMillis();
            long finalTime = currentTime + 12000;
            while (currentTime < finalTime) {
                population_selection = select();
                if (Math.floorMod(currentTime, 143) == 0) {
                    //System.out.println("Mutation, currentTime is " + currentTime);
                    mutation(population_selection[1]);
                }
                crossover(population_selection[0], population_selection[1]);
                currentTime = System.currentTimeMillis();
            }
            population_selection = select();
        //    System.out.println("Solution : "+ sample_counter + "\nOS " + Arrays.toString(OSpopulation.get(population_selection[0])) + "\nMA " + Arrays.toString(MApopulation.get(0)) + "\nFitness " + fitness(OSpopulation.get(population_selection[0]), MApopulation.get(0)));

            sample_array[sample_counter] = fitness(OSpopulation.get(population_selection[0]), MApopulation.get(0)); // store the result in array
        }


        BarChartDemo demo = new BarChartDemo("JFreeChart: BarChartDemo1.java",sample_array, smaple_nummber);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);


    }


    public static void whenWriteStringUsingBufferedWritter_thenCorrect() throws IOException {


        String FILENAME = "d:\\e.txt";
        BufferedWriter bw = null;
        FileWriter fw = null;
        String content = "This is the content to write into file\n";

        try {
            fw = new FileWriter(FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bw = new BufferedWriter(fw);
            bw.write(content);



    }















    public static void mutation(int sp ){
        boolean con= Math.random()>0.5;
        Integer[] OSM = new Integer[nTasks];
        int index1 ;
        int index2 ;
        // TODO mutation on MA change if(true) to if con
        if (true) {  //working on OS with
            index1=(int)(Math.floor(Math.random()*nTasks));
            index2=(int)(Math.floor(Math.random()*nTasks));

            OSM= Arrays.copyOfRange(OSpopulation.get(sp),0,nTasks);
            Integer x= OSM[index1];
            OSM[index1]=OSM[index2];
            OSM[index2]=x;
            OSpopulation.add(OSM);
            //System.out.println(Arrays.toString(OSM) + " index1 " + index1 + " index2 " + index2);

        }


            else {   // Working on MA
            Integer[] MA = MApopulation.get(sp);
            ArrayList<Integer[]> taskMutable = new ArrayList<>();
            for(Job j : jobs){
                for(Task t : j.getTasks()){
                    if(t.getNbMachines()>1){
                        Integer selectedProcess[] = t.getSelectedProcess();
                        boolean conMa = false ;
                        while(!conMa){

                        }
                    }

                }
            }
        }
        //System.out.println("OSM " + Arrays.toString(OSM)+ "\n index switch " + index1 + " "  +index2);

    }                          // sp stand for select population
    public static void crossover(int sp1,int sp2) {
        int length=OSpopulation.size();
        int index1,index2;
        boolean crossOK = false ;
        Integer[] OSM1,OSM2,OSM3;  //pop 1


        if(sp1 > length || sp2 >length )
        {System.out.println(" vous avez depassé la taille");}
        else {

            OSM1 = OSpopulation.get(sp1);
            OSM2 = OSpopulation.get(sp2);
            OSM3 = new Integer[OSM1.length];
            while(!crossOK){
                index1 = (int) (Math.random() * nTasks);

                for (int i = 0; i < nTasks; i++) {

                    if (i < index1)
                        OSM3[i] = OSM1[i];
                    else
                        OSM3[i] = OSM2[i];

                }
                try {
                    fitness(OSM3, MApopulation.get(0));
                    crossOK = true;
                } catch (java.lang.ArrayIndexOutOfBoundsException e) {
                //    System.out.println("Crossover Failed");
                    crossOK = false;
                }
            }
            OSpopulation.add(OSM3);

        //System.out.println("POPULATION 1 "+Arrays.toString(OSM1)+"\n"+"POPULATION 2 "+ Arrays.toString(OSM2)+"\n"+"population final"+Arrays.toString(OSM3)+"\n");


            for (int j=0;j<OSpopulation.size();j++)
            {
              //System.out.println(Arrays.toString(OSpopulation.get(j))+"\n");
            }
            }
    }
    public static int [] select(){


        int[] fitness_OS =new int[OSpopulation.size()];
      //  r[0] = (int) (Math.random()*OSpopulation.size());
       // r[1]= (int) (Math.random()*OSpopulation.size());
        int min1=Integer.MAX_VALUE;
        int indexMin1 = 0 ;
        int min2=Integer.MAX_VALUE;
        int indexMin2 = 0 ;
        int max = Integer.MIN_VALUE;
        int indexMax = 0 ;


        for (int i=0;i< OSpopulation.size();i++) {
            //System.out.println("i select " + i + "\n OS " + Arrays.toString(OSpopulation.get(i)));
            fitness_OS[i] = fitness(OSpopulation.get(i), MApopulation.get(0));
            if (fitness_OS[i] > max ){
                indexMax = i ;
                max = fitness_OS[i];
            }
            if (fitness_OS[i] < min1) {
                indexMin2 = indexMin1 ;
                min2 = min1 ;
                indexMin1 = i ;
                min1 = fitness_OS[i];
            } else if (fitness_OS[i] < min2) {
                indexMin2 = i ;
                min2 = fitness_OS[i];
            }
        }
        OSpopulation.remove(indexMax);
        if(indexMax< indexMin1)
            indexMin1 -= 1 ;
        if(indexMax< indexMin2)
            indexMin2 -= 1 ;
        //MApopulation.remove(indexMax);
        //System.out.println("test fitness Array : " + Arrays.toString(fitness_OS));
        //System.out.println("test min " + indexMin1 + " "  + indexMin2 + " max " + indexMax);
        int res[] = new int[2];
        res[0] = indexMin1 ;
        res[1] = indexMin2 ;
        return res ;
    }
    public static void readFile(String name) {
        try {
            File f = new File(name);
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);

            try {
                String line = br.readLine();
                String[] first_line_split = line.split("\\s+");
                nJobs = Integer.parseInt(first_line_split[0]);
                nMachines = Integer.parseInt(first_line_split[1]);
                if(first_line_split.length > 2)
                    avgMachinesperJob = Integer.parseInt(first_line_split[2]);
                jobs = new Job[nJobs];
                machines = new Integer[nMachines];
                machinesTime = new Integer[nMachines];
                for (int k = 0; k < nMachines ; k++) {
                    machines[k] = 0;
                    machinesTime[k] = 0;
                }
                Job currentJ ;
                Task currentT ;
                Integer iT = 0;
                Integer iJ = 0;
                line = br.readLine();
                while (line != null) {
                    // System.out.println(line);
                    String[] line_split = line.split("\\s+");
                    currentJ = new Job(Integer.parseInt(line_split[0]),iJ+1);
                    iT = 1 ;
                    for(int j = 0; j < currentJ.getNbTasks(); j++) {
                        currentT = new Task(Integer.parseInt(line_split[iT++]),j+1);
                        for (int i = 0; i < currentT.getNbMachines(); i++) {
                            currentT.addProcess(Integer.parseInt(line_split[iT++]), Integer.parseInt(line_split[iT++]));
                        }
                        currentJ.addTask(currentT);
                    }
                    nTasks += currentJ.getNbTasks();
                    jobs[iJ++] = currentJ ;
                    line = br.readLine();
                }
                //OS = new Integer[nTasks];
                //MA = new Integer[nTasks];
                br.close();
                fr.close();

                for(int i = 0 ; i < nJobs ; i++){
                    if (jobs[i].getNbTasks()>nbMaxTasksperJob)
                        nbMaxTasksperJob = jobs[i].getNbTasks();

                }
            } catch (IOException exception) {
                System.out.println("Erreur lors de la lecture : " + exception.getMessage());
            }
        } catch (FileNotFoundException exception) {
            System.out.println("Le fichier n'a pas été trouvé");
        }
    }
    public static void selectMachine(){
        int nbMaxT = 0 ;

        Task currentT ;

        for(int i = 0 ; i < nJobs ; i++){
            if (jobs[i].getNbTasks()>nbMaxT)
                nbMaxT = jobs[i].getNbTasks();

        }

        for(int j = 0 ; j < nbMaxT ; j++) {
            for (int i = 0; i < nJobs; i++) {
                if (j < jobs[i].getNbTasks()) {
                    currentT = jobs[i].getTask(j);
                    currentT.selectBest();

                }
            }
        }

    }
    public static Integer[] buildMA(){
        int i = 0 ;
        Integer MA[] = new Integer[nTasks];
        for (Job j : jobs){
            for (Task t : j.getTasks()){
                MA[i++] = t.getSelectedProcess()[0];
            }
        }
        return MA ;
    }
    public static void solveconflict(){
        int machine ;
        int time ;

        Task currentTask ;
        // Start time ne sert à rien pour l'instant
        for(int j = 0 ; j < nbMaxTasksperJob ; j++) {
            for (int i = 0; i < nJobs; i++) {
                if (j < jobs[i].getNbTasks()) {
                    currentTask = jobs[i].getTask(j);
                    machine = currentTask.getSelectedProcess()[0]-1;
                    time = currentTask.getSelectedProcess()[1];
                    currentTask.setStartTime(machinesTime[machine]);
                    machinesTime[machine] += time;
                    //System.out.println("job " + i + " task " + j + " machine " + machine + " current Task starting time " + currentTask.getStartTime() );

                }
            }
        }
    }
    public static Integer[] buildOS(){
        Integer startingTimes[][] = new Integer[2][nTasks];
        //Integer idJobs[] = new Integer[nTasks];
        int k = 0 ;


        for(int j = 0 ; j < nbMaxTasksperJob ; j++) {
            for (int i = 0; i < nJobs; i++) {
                if (j < jobs[i].getNbTasks()) {
                    startingTimes[1][k] = jobs[i].getTask(j).getStartTime();
                    startingTimes[0][k++] = jobs[i].getId();
                }
            }
        }
        sortTimes(startingTimes);
        return startingTimes[0];
    }
    public static void sortTimes(Integer tab[][]) {
        int length =tab[0].length;
        boolean swap;

        do
        {
            swap=false;

            for(int i=0;i<length-1;i++)
            {
                if(tab[1][i]>tab[1][i+1])
                {
                    Integer aux0 = tab[0][i];
                    Integer aux1 = tab[1][i];
                    tab[0][i] = tab[0][i+1];
                    tab[1][i] = tab[1][i+1];
                    tab[0][i+1] = aux0 ;
                    tab[1][i+1] = aux1 ;
                    swap=true;
                }
            }
            length--;
        }
        while(swap);
    }
    //TODO trace chemin critique
    public static int fitness(Integer OS[] , Integer MA[]){     //fitnesse function
        int machineEndTime[] = new int[nMachines];
        int jobsEndTime[] = new int[nJobs];
        //
        int jobsN[] = new int[nJobs];
        ArrayList<Integer[]> jobsM = new ArrayList<>();
        int index = 0 ;
        for(int i = 0 ; i < nJobs ; i ++){
            jobsM.add(Arrays.copyOfRange(MA,index, index+jobs[i].getNbTasks()));
            index += jobs[i].getNbTasks();
        }
        for(int i : OS){
            Task currentTask = jobs[i-1].getTask(jobsN[i-1]);
            int currentMachine = currentTask.getSelectedProcess()[0];
            int time = currentTask.getSelectedProcess()[1];
            int max = jobsEndTime[i-1] ;
            if(machineEndTime[currentMachine-1]> jobsEndTime[i-1]){
                max = machineEndTime[currentMachine-1];
            }
            machineEndTime[currentMachine-1] = max + time ;
            jobsEndTime[i-1] = max + time ;
            jobsN[i-1]++;
        }
        //System.out.println("Machine End time " + Arrays.toString(machineEndTime) + " jobs end time " + Arrays.toString(jobsEndTime));
        Arrays.sort(jobsEndTime);
        Arrays.sort(machineEndTime);
        if(jobsEndTime[nJobs-1] == machineEndTime[nMachines-1])
            return jobsEndTime[nJobs-1];
        else
            return -1;
    }
    public static int max(int[] t) {
        int maximum = t[0];   // start with the first value
        for (int i=1; i<t.length; i++) {
            if (t[i] > maximum) {
                maximum = t[i];   // new maximum
            }
        }
        return maximum;
    }
}