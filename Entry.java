/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entry;
import java.io.*;
import java.util.*;
import java.util.ArrayList;
/**
 *
 * @author leoca
 */
public class Entry {

    /**
     * Public Scanner k
     */
    public static Scanner k = new Scanner(System.in);

    /**
     * Month
     */
    public String month;

    /**
     * Day
     */
    public int day;

    /**
     * Year
     */
    public int year;

    /**
     * Temperature
     */
    public float temp;
    /**
     * Public Entry
     * Creates Entry Class storing the necessary variables
     * @param month
     * @param day
     * @param year
     * @param temp
     * @throws IOException 
     */
    
    public Entry(String month, int day, int year, float temp) throws IOException{
        this.month = month;
        this.day = day;
        this.year = year;
        this.temp = temp;
    }
    /**
     * Setter for month
     * @param m 
     */
    public void setMonth(String m){
        this.month = m;
    }

    /**
     * Setter for Day
     * @param d
     */
    public void setDay(int d){
        this.day = d;
    }

    /**
     * Setter for Year
     * @param y
     */
    public void setYear(int y){
        this.year = y;
    }

    /**
     * Setter for Temp
     * @param t
     */
    public void setTemp(float t){
        this.temp = t;
    }

    /**
     * Getter for month
     * @return
     */
    public String getMonth(){
        return month;
    }

    /**
     * Getter for Day
     * @return
     */
    public int getDay(){
        return day;
    }

    /**
     * Getter for Year
     * @return
     */
    public int getYear(){
        return year;
    }

    /**
     * Getter for Temp
     * @return
     */
    public float getTemp(){
        return temp;
    }
    /**
     * uploadTemperatures
     * This takes a file that is created in main and uploads it into the ArrayList uploadData
     * Then Prints that it is successful in doing so
     * @param uploadData
     * @throws IOException
     * Option 1
     */
    public static void uploadTemperatures(ArrayList<Entry> uploadData)throws IOException{
        String month;
        int day, year;
        float temp;
        String inputFile;
        
        System.out.println("\nWhat is the name of the file you want to upload data from?");
        inputFile = k.nextLine();
        File file = new File(inputFile);
        Scanner fileScan = new Scanner(file);
        
        while(fileScan.hasNext()){
            month = fileScan.next();
            day = fileScan.nextInt();
            year = fileScan.nextInt();
            temp = fileScan.nextFloat();
            uploadData.add(new Entry(month, day, year, temp));
        }
        System.out.println("***Data uploaded successfully.\n");
    }
    /**
     * printTemperatureData
     * This method takes our main ArrayList uploadData and prints it's data in a slightly altered format
     * You must upload a file in option 1 before doing this
     * @param uploadData
     * Option 2
     * @throws java.io.IOException
     **/ 
    public static void printTemperatureData(ArrayList<Entry> uploadData) throws IOException{
        System.out.println();
        for(Entry data: uploadData){
            System.out.println("Date: " + data.month + " " + data.day + ", " + data.year + "  Temperature: " + data.temp);  
        }
        System.out.println();
    
    }
    /**
     * generateStatsFile
     * This method takes our main ArrayList and creates a stats file containing the data
     * First it takes only the temperatures and sorts them from least to greatest descending
     * Second it finds the lowest temperature and prints the date this temperature was recorded
     * Third it gets the average temperatures for all the days recorded and takes the average of them and presents that number in the stats file
     * Fourth it gets the averages from each month and presents them as monthly averages
     * @param uploadData
     * Option 3
     * @throws java.io.IOException
     **/
    public static void generateStatsFile(ArrayList<Entry> uploadData) throws IOException{
        PrintWriter fWriter = new PrintWriter("stats.txt");
        ArrayList<Float> tempList = new ArrayList<>(10);
        
        //Sorting highest to lowest
        fWriter.println("\nA: Temperatures sorted from highest to lowest: ");
        for(Entry stats: uploadData){//Create Entrry - stats and read temperatures into it
            tempList.add(stats.temp);
            Collections.sort(tempList);//Sorts the temperatures lowest to highest
            Collections.reverse(tempList);//sort temperatures reverse to get it correct
        }
            for(Float i: tempList){//creates i and prints the temperatures in order
                fWriter.println(i);//prints i in each line
            }
        
        
        //Find lowest temp
        Collections.sort(tempList);//Sort the temperatures back to least to lowest to highest is easy to find
        float low = tempList.get(0);//lowest temp
        for(Entry lowTemp: uploadData){//Creates lowTemp entry to read lowest temp line
            if(lowTemp.temp == low){ //print whole line from upload data
                fWriter.println("\nB: The day with the lowest temperature was " + lowTemp.month + " " + lowTemp.day + ", " + lowTemp.year + ", and the temperature was " + low + ".\n");
            }
        }
       

        
        //Average of all temps
        float total1 = 0;
        for(Entry w: uploadData){
            total1 += w.temp;
        }
        
        fWriter.printf("C: The average temperature for all days is %.1f.\n", total1/uploadData.size());
        
        
        //Monthly averages
        fWriter.println("\nD: Monthly Averages:");
        String [] months2 ={"January", "February", "March", "May", "July", "November"};//Array of month names to read in from the file easy
        int count = 0;//Incremental
        float total = 0;//Accumulator
        for(String y: months2){//creates y to keep track of months
            for(Entry monthAvg: uploadData){//Creates Entry monthAvg
                if(y.equals(monthAvg.month)){//if the month that the user selected is in the file, the program will get the average temps
                    count++;
                    total = (total + monthAvg.temp);//adding the temp for that specific month
                }
            }
            if(count>0){//if there was a month that was found
                float avg = total/count;//calculating average
                fWriter.printf("* " + y + "'s average temperature is %.1f\n", avg);
            }
            count = 0;//resetting variables
            total = 0;
        }
        System.out.println("\nCreated statistics file named stats.txt.\n");
        fWriter.close();//CLOSES FILE WRITER
    }
    
    /**
     * printStatsFile
     * This method prints out stats file to the screen directly
     * You must create the stats file before attempting to see it on the screen
     * @throws IOException 
     */
    //Option 4 print the file to the screen
    public static void printStatsFile() throws IOException{
        File statsFile = new File("stats.txt");//takes in file called stats
        Scanner statsReader = new Scanner(statsFile);//scans through stats file
        while(statsReader.hasNextLine()){//reads and prints the file data
            String data = statsReader.nextLine();
            System.out.println(data);
        }
    } 
    /**
     * printMonth 
     * This method prints the month's data for whatever month the user chooses
     * Takes in our main ArrayList uploadData and creates a new Entry called m to scan through the months in the ArrayList
     * When the user enters a month that is present it prints its data as the incremental number goes up to tell it to stop
     * @param uploadData
     * Option 5
     */
    public static void printMonth(ArrayList<Entry> uploadData){
        int incr = 0;//incremental
        System.out.println("\nWhich month would you like to see?");
        String printMonth = k.nextLine();//get month from user
        for(Entry m: uploadData){//creates Entry m
            if(printMonth.equals(m.month)){//if the user enters a month that is in the Array, it will print it for as long as it finds it
                incr++;
                System.out.println("Date: " + m.month + " " + m.day + ", " + m.year + "  Temperature: " + m.temp);
            }
        }
        if(incr == 0){
            System.out.println("No data available.");
        }
    }

    /**
     * Main Method
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        
        PrintWriter pw1 = new PrintWriter("input.txt");
        pw1.println("January 10 2021 22.9");
        pw1.println("January 11 2021 41.5");
        pw1.println("January 12 2021 18.9");
        pw1.println("January 13 2021 24.9");
        pw1.println("February 2 2021 19.7");
        pw1.println("February 3 2021 14.6");
        pw1.println("February 4 2021 20.8");
        pw1.println("February 5 2021 18.2");
        pw1.println("March 10 2021 16.3");
        pw1.println("March 11 2021 17.4");
        pw1.close();
        PrintWriter pw2 = new PrintWriter("input2.txt");
        pw2.println("January 10 2021 23.9");
        pw2.println("March 11 2021 36.6");
        pw2.println("May 12 2021 47.1");
        pw2.println("July 13 2021 64.9");
        pw2.println("July 14 2021 62.3");
        pw2.println("November 15 2021 28.6");
        pw2.println("November 2 2021 19.7");
        pw2.close();
        
        
        ArrayList<Entry> uploadData = new ArrayList<>();
        int option;
        
        while(true){
        System.out.println("Welcome to the Temperature Tracker and Analyzer program. Please choose from the following options:");
        System.out.println("1. Upload Data");
        System.out.println("2. View Data");
        System.out.println("3. Create Statistics File");
        System.out.println("4. Print Statistics File");
        System.out.println("5. Print Month");
        System.out.println("6. Exit the program");
        option = k.nextInt();
        k.nextLine(); // ignore input
        
        switch(option){
            case 1:{ //Creating a file
                uploadTemperatures(uploadData);
                break;
            }
            case 2:{ //viewing the data in file
                printTemperatureData(uploadData);
                break;
            }
            case 3:{//creating stats.txt
                generateStatsFile(uploadData);
                break;
            }
            case 4:{//prnting the contents of stats.txt
                printStatsFile();
                System.out.println();
                break;
            }
            case 5:{//printing all of the data from a certain month
                printMonth(uploadData);
                System.out.println();
                break;
            }
            case 6:{
                System.exit(0);
            }
            
        }
        
        }
        
    }
    
}

    
    

