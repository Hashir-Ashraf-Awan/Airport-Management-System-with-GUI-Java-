
import java.awt.*;
import java.util.*;
import GlobalClock;
import ControlLogic;
import GroundMonitor;
class airport extends Frame{
public Panel Button_Panel;
public Button Pause_Button;
public Button Resume_Button;
public Button Exit_Button;
public Button Stop_Button;
public TextField ArrivalRateTextField;
public TextField DepartureRateTextField;
AirplaneScheduler FlightScheduler;
 AirplaneFleet Airline;
boolean current_state;
GlobalClock Clock;
MainCanvas GroundView;
public airport(){
super(“Airport Simulation Example”);
Clock = new GlobalClock(10);
 GroundMonitor.createAllQueues();
 GroundMonitor.createAllNetPaths();
 TrafficParam.initiateTrafficParaClass();
 TrafficParam.setAllTrafficParameters();
AirplaneScheduler FlightScheduler = new AirplaneScheduler();
AirplaneFleet Airline = new AirplaneFleet();
current_state = true;
Button_Panel = new Panel();
Pause_Button = new Button(“Pause”);
Resume_Button = new Button(“Resume”);
Stop_Button = new Button(“Run”);
Exit_Button = new Button(“Exit”);
Button_Panel.add(Pause_Button);
Button_Panel.add(Resume_Button);
//Button_Panel.add(Stop_Button);
Button_Panel.add(Exit_Button);
/* ArrivalRateTextField = new TextField(3);
DepartureRateTextField = new TextField(3);
Button_Panel.add(ArrivalRateTextField);
Button_Panel.add(DepartureRateTextField);
 */
GroundView = new MainCanvas(FlightScheduler, Airline);
 this.add(“Center”, GroundView);
this.add(“South”, Button_Panel);
 this.resize(600, 400);
 this.show();
}
 static public void main(String[] args) {
airport myairport = new airport();
 }
public boolean action(Event event, Object arg) {
System.out.println(“In Event action”);
if(event.target == Pause_Button)
{
System.out.println(“Pasue”);
Clock.ClockThread.suspend();
GroundView.AnimationEngine.suspend();
}else if (event.target == Resume_Button)
{
System.out.println(“Resume”);
GroundView.AnimationEngine.resume();
Clock.ClockThread.resume();
}else if (event.target == Exit_Button)
{
System.exit(0);
}else if (event.target == Stop_Button){
if(current_state == true){
GroundView.AnimationEngine.stop();
Airline.runAirplane.stop();
//FlightScheduler.CreateFlightThread.stop();
Clock.ClockThread.stop();
GroundView.AnimationEngine= null;
Airline.runAirplane = null;
Clock.ClockThread = null;
//FlightScheduler.CreateFlightThread = null;
current_state = false;
}
else
{
System.out.println(“re-activate thread”);
current_state = true;
Clock.startClock();
 System.out.println(“here1”);
//FlightScheduler.startScheduler();
//Airline.startRun();
//GroundView.startAnimation();
}
}
return true;
}
}
class MainCanvas extends Canvas implements Runnable {
 public Font TitleFont = new Font(“Helvetica”, Font.BOLD, 18);
 public Font AirplaneFont = new Font(“Helvetica”, Font.PLAIN, 10);
 Image backGroundBuffer, workPadBuffer;
 Graphics backGroundGraphics, workPadGraphics;
 AirportGroundMap groundMap = new AirportGroundMap();
 AirplaneScheduler FlightScheduler;
 AirplaneFleet Airline;
 boolean upDateBackGround;
 Thread AnimationEngine;
GlobalClock Clock = new GlobalClock();
 MainCanvas(AirplaneScheduler scheduler, AirplaneFleet fleet) {
 FlightScheduler = scheduler;
 Airline = fleet;
 AnimationEngine = new Thread(this);
 AnimationEngine.setPriority(6);
 AnimationEngine.start();
 upDateBackGround = true;
 }
public void startAnimation(){
 AnimationEngine = new Thread(this);
 AnimationEngine.setPriority(6);
 AnimationEngine.start();
}
 public void paint(Graphics g) {
 update(g);
 }
 public synchronized void update(Graphics g) {
 int w = bounds().width;
 int h = bounds().height;
 if (backGroundBuffer == null
 || backGroundBuffer.getWidth(null) != w
104
 || backGroundBuffer.getHeight(null) != h) {
 backGroundBuffer = createImage(w, h);
 if(backGroundBuffer != null) {
 if(backGroundGraphics != null) {
 backGroundGraphics.dispose();
 }
 backGroundGraphics = backGroundBuffer.getGraphics();
 }
 }
 if (backGroundBuffer != null) {
 backGroundGraphics.setColor(Color.white);
 backGroundGraphics.fillRect(0, 0, w, h);
 groundMap.drawMap(backGroundGraphics);
 }
if(!FlightScheduler.AirplaneList.isEmpty())
 for(int i = 0; i < FlightScheduler.AirplaneList.size(); i++){
// System.out.println(“size = “ + (int)FlightScheduler.AirplaneList.size());
 Airplane tempAirplane = (Airplane) FlightScheduler.AirplaneList.elementAt(i);
 tempAirplane.updateCurrentLocation();
 if(tempAirplane.MovingTrack != null &&
 tempAirplane.CurrentQueue != null &&
 tempAirplane.crrntPoint != null)
 {
 if(tempAirplane.delayIndicator == true)
 backGroundGraphics.setColor(Color.red);
 else
 if(tempAirplane.RoutName.compareTo(“ENROUT”) == 0)
 backGroundGraphics.setColor(Color.blue);
 else
 backGroundGraphics.setColor(Color.green);
 backGroundGraphics.fillOval(tempAirplane.crrntPoint.x,
 tempAirplane.crrntPoint.y, 6, 6); //draw airplane
 backGroundGraphics.setColor(Color.black);
 backGroundGraphics.drawOval(tempAirplane.crrntPoint.x,
 tempAirplane.crrntPoint.y, 6, 6); //draw airplane
 backGroundGraphics.setFont(AirplaneFont);
 backGroundGraphics.drawString(tempAirplane.getAircraftName(),
 tempAirplane.crrntPoint.x,
 tempAirplane.crrntPoint.y); //draw airplane
backGroundGraphics.setColor(Color.black);
tempAirplane.printTime(backGroundGraphics, 400, 120);
 }
 if(tempAirplane.AirplaneFinalStop == true){
 FlightScheduler.AirplaneList.removeElement(tempAirplane);
 }
 }
 backGroundGraphics.setFont(TitleFont);
 backGroundGraphics.drawString(“Airport Ground Traffic Monitor”,
 150,
 60); //draw airplane
 backGroundGraphics.setColor(Color.green);
 backGroundGraphics.fillOval(170, 90, 10, 10);
 backGroundGraphics.setFont(AirplaneFont);
 backGroundGraphics.setColor(Color.black);
 backGroundGraphics.drawString(“Arrival Airplanes”, 190, 100);
 backGroundGraphics.setColor(Color.blue);
 backGroundGraphics.fillOval(170, 110, 10, 10);
 backGroundGraphics.setColor(Color.black);
105
 backGroundGraphics.drawString(“Departure Airplanes”, 190, 120);
 backGroundGraphics.setColor(Color.red);
 backGroundGraphics.fillOval(170, 130, 10, 10);
 backGroundGraphics.setColor(Color.black);
 backGroundGraphics.drawString(“Delayed Airplanes”, 190, 140);
Clock.printTime(backGroundGraphics, 400, 100);
 g.drawImage(backGroundBuffer, 0, 0, this);
 }
 public void run() {
 while (true) {
 repaint();
 try{AnimationEngine.sleep(100);} catch (Exception e) {};
 }
 }
}
class Airplane extends Object {
 long freeFlowTime = 0;
long delay;
static long AvTotal;
static long AvArrival;
static long AvDepart;
static long ArrivalTravelTime;
static long DepartureTravelTime;
static int numberOfArrival;
static int numberOfDeparture;
public void printTime(Graphics g, int x, int y){
if(numberOfArrival > 0 && numberOfDeparture > 0){
g.drawString(“Arrival Delay : “+ AvArrival+” seconds”, x, y);
g.drawString(“Departure Delay : “+ AvDepart+” seconds”, x, y+15);
g.drawString(“Arrival:”+ numberOfArrival+” Departure: “+numberOfDeparture,
 x, y+30);
}
}
 public String getAircraftName()
 {
 return AircraftName;
 }
 public boolean isCategory(String cat)
 {
 if(Category.equals(cat))
 return true;
 else
 return false;
 }
 public boolean delayIndicator;
 public synchronized void updateCurrentLocation()
 {
 if(MovingTrack != null && MovingTrack.Track.size() > 0)
 {
 crrntPoint = (Point) MovingTrack.Track.elementAt(0);
 MovingTrack.Track.removeElementAt(0);
 }
 }
 public synchronized void updateTrack()
 {
 if(CurrentQueue != null && NextQueue != null)
 MovingTrack = new LineObject(CurrentQueue.From, NextQueue.From);
 else
 MovingTrack = null;
 }
 public String RoutName;
 public void setAirplaneCategory(String cat_name)
106
107
 {
 Category = new String(cat_name);
 }
 public void setAircraftName(String aircraft_name)
 {
 AircraftName = new String(aircraft_name);
 }
 private String AircraftName;
 private String Category;
 int step_counter = 0;
 public LineObject MovingTrack;
 public Airplane(int flow)
 {
 total++;
 ID = total;
 flow_ID = flow;
 startTime = Watch.getTime();
 resetTimer();
 setStateToApproach();
 }
 public synchronized void setAirplaneFinalStop()
 {
 AirplaneFinalStop = true;
 finishTime = Watch.getTime();
 long throughput = finishTime - startTime;
 delay = throughput - freeFlowTime;
 System.out.println(ID()+” “+AircraftName+” “+RoutName+” follow “+pathName+
 “ used “+throughput+” seconds”+” from “+startTime+
 “ to “+finishTime);
if(“ENROUT”.equals(RoutName))
{
//DepartureTravelTime +=throughput;
DepartureTravelTime +=delay;
numberOfDeparture++;
if(numberOfDeparture > 0)
AvDepart = DepartureTravelTime/numberOfDeparture;
}
else
{
//ArrivalTravelTime +=throughput;
ArrivalTravelTime +=delay;
numberOfArrival++;
if(numberOfArrival> 0)
AvArrival = ArrivalTravelTime/numberOfArrival;
}
if(numberOfArrival > 0 || numberOfDeparture > 0)
AvTotal = ( ArrivalTravelTime + DepartureTravelTime)/
(numberOfArrival + numberOfDeparture);
 }
 public boolean timerTimeOut()
 {
 return (timer <= Watch.getTime()? true:false);
 }
 public long getTimer()
 {
 return timer;
 }
 public void resetTimer()
 {
 timer = 0;
 }
 public String pathName;
 long timer;
 public void setTimer(long second)
 {
 if(second < 0)
 {
 System.out.println(“exit here”);
 System.exit(1);
 }
 timer = Watch.getTime() + second;
 freeFlowTime +=second;
 if(MovingTrack != null)
 MovingTrack.setTrack((int) second);
 }
 public static final int TAKEOFF = 4;
 public static final int APPROACH = 0;
 public static final int DEPART = 1;
 public static final int TAXI = 5;
 public static final int LAND = 3;
 public int State;
 public AirplaneQueue NextQueue;
 public AirplaneQueue CurrentQueue;
 // public ControlLogic TrafficGuid = new ControlLogic();
 static int total;
 private int ID;
 Point prevPoint = new Point(0, 0);
 public Point crrntPoint;
 AirportGroundMap groundMap = new AirportGroundMap();
 public int currentLink = 0;
 public int flow_ID = 0;
 GlobalClock Watch = new GlobalClock();
 Point[] destination = new Point[2];
 public boolean AirplaneFinalStop = false;
 public long startTime;
 public long finishTime;
 SpeedController moveGuid = new SpeedController(Watch.interval);
 boolean moveFlag;
 public final static int airplaneTick = 100;
 Airplane(int setPath, int x, int y) {
 total++;
 ID = total;
 destination[0] = new Point(300, 100);
 destination[1] = new Point(380, 100);
 flow_ID = setPath;
 crrntPoint.x = x;
 crrntPoint.y = y;
 startTime = Watch.getTime();
 resetTimer();
 setStateToApproach();
 }
 public int getMovingStep(int TimeInSecond) {
 return (int)(moveGuid.getFastMoveStep(TimeInSecond, airplaneTick));
 }
 public void setSpeed(Point From, Point To, int deltaTime)
 {
 moveGuid.setSpeedProfile(From, To, startTime,
 startTime + deltaTime);
 }
 public int ID() { return ID;}
 public synchronized void setStateToApproach() {
 State = APPROACH;
 }
 public synchronized void setStateToLand() {
 State = LAND;
 }
 public synchronized void setStateToTaxi() {
 State = TAXI;
 }
 public synchronized void setStateToTakeoff() {
 State = TAKEOFF;
 }
 public synchronized void setStateToDepart() {
 State = DEPART;
 }
 public boolean isApproaching()
108
 {
 return (State == APPROACH? true:false);
 }
 public boolean isLanding()
 {
 return (State == LAND? true:false);
 }
 public boolean isTaxiing()
 {
 return (State == TAXI? true:false);
 }
 public boolean isTakeoff()
 {
 return (State == TAKEOFF? true:false);
 }
 public boolean isDeparting()
 {
 return (State == DEPART? true:false);
 }
 public boolean equals(Airplane comparedAirplane) {
 if(comparedAirplane.ID() == ID)
 return true;
 else
 return false;
 }
 public int random(int rd) {
 return (int) Math.floor(Math.random()*rd);
 }
 public void setTrackPoint(Point TrackPoint) {
 prevPoint.x = TrackPoint.x;
 prevPoint.y = TrackPoint.y;
 }
 public synchronized void run() {
 if(AirplaneFinalStop != true)
 {
 //Operation control
 if(timerTimeOut())
 ControlLogic.controlAllAirplanes(this);
 }
 }
}
class AirportGroundMap {
 public Point[] nodes = new Point[5];
 Point[] link_2 = new Point[2];
 Point[] link_3 = new Point[2];
 Point[] link_4 = new Point[2];
 Point[] terminalPoints = new Point[2];
 int delta = 4;
 AirportGroundMap() {
 nodes[0] = new Point(50, 300);
 nodes[1] = new Point(250, 300);
 nodes[2] = new Point(380, 300);
 nodes[3] = new Point(380, 100);
 nodes[4] = new Point(300, 100);
 terminalPoints[0]= new Point(300, 100);
 terminalPoints[1]= new Point(380, 100);
 }
 public void drawLinks(Graphics g) {
 /*
 g.drawLine(nodes[0].x, nodes[0].y,
109
 nodes[1].x, nodes[1].y);
 g.drawLine(nodes[1].x, nodes[1].y,
 nodes[2].x, nodes[2].y);
 g.drawLine(nodes[2].x, nodes[2].y,
 nodes[3].x, nodes[3].y);
 g.drawLine(nodes[1].x, nodes[1].y,
 nodes[4].x, nodes[4].y);
 */
 for(int i = 0; i < GroundMonitor.queues.size(); i++)
 {
 AirplaneQueue tempQ = (AirplaneQueue)
 GroundMonitor.queues.elementAt(i);
 if(tempQ != null)
 {
 if(tempQ.From.equals(tempQ.To))
 g.drawOval(tempQ.From.x, tempQ.From.y, 3, 3);
 else
 g.drawLine(tempQ.From.x, tempQ.From.y,
 tempQ.To.x, tempQ.To.y);
 }
 }
 }
 public void drawPath(Graphics g, int Path) {
 if(Path == 1) {
 g.drawLine(nodes[0].x, nodes[0].y,
 nodes[1].x, nodes[1].y);
 g.drawLine(nodes[1].x, nodes[1].y,
 nodes[2].x, nodes[2].y);
 g.drawLine(nodes[2].x, nodes[2].y,
 nodes[3].x, nodes[3].y);
 }
 if(Path == 2) {
 g.drawLine(nodes[0].x, nodes[0].y,
 nodes[1].x, nodes[1].y);
 g.drawLine(nodes[1].x, nodes[1].y,
 nodes[4].x, nodes[4].y);
 }
 }
 public void drawTerminal(Graphics g, int Terminal)
 {
 for(int i = 0; i < GroundMonitor.queues.size(); i++)
 {
 AirplaneQueue tempQ = (AirplaneQueue)
 GroundMonitor.queues.elementAt(i);
 if(tempQ != null && tempQ.From.equals(tempQ.To))
 g.fillOval(tempQ.From.x, tempQ.From.y, 2, 2);
 }
 // g.fillOval(terminalPoints[Terminal].x,
 // terminalPoints[Terminal].y, 20, 30);
 }
 public void drawMap(Graphics g) {
 g.setColor(Color.black);
 drawLinks(g);
 g.setColor(Color.green);
 drawTerminal(g, 0);
 g.setColor(Color.pink);
 drawTerminal(g, 1);
 }
 public void getNextPointToMove(Airplane TempAirplane,
110
 long LocalTime)
 {
 Point currentPoint = TempAirplane.crrntPoint;
 if(TempAirplane.flow_ID == 0) {
 //set flag runway
 if(currentPoint.x >=50 && currentPoint.x <380)
 {
 if(TempAirplane.moveGuid.flag != “runway”)
 {
 TempAirplane.moveGuid.flag = “runway”;
 TempAirplane.setSpeed(nodes[0], nodes[2], 50);
 delta = TempAirplane.getMovingStep(50);
 TempAirplane.moveGuid.setTimeSpaceTable(
 nodes[0], nodes[2],
 LocalTime, 50);
 }
 Point nextPoint =
 TempAirplane.moveGuid.getNextCoordinate(LocalTime);
 if(nextPoint != null)
 currentPoint.move(nextPoint.x, nextPoint.y);
 }
 //set flag taxiway
 if(currentPoint.x >= 380 && currentPoint.y <= 300
 && currentPoint.y > 100)
 {
 if(TempAirplane.moveGuid.flag != “taxiway”)
 {
 TempAirplane.moveGuid.flag = “taxiway”;
 TempAirplane.setSpeed(nodes[2], nodes[3], 100);
 TempAirplane.moveGuid.setTimeSpaceTable(
 nodes[2], nodes[3],
 LocalTime, 100);
 }
 Point nextPoint =
 TempAirplane.moveGuid.getNextCoordinate(LocalTime);
 if(nextPoint != null)
 currentPoint.move(nextPoint.x, nextPoint.y);
 }
 //set flag terminal
 if(currentPoint.y <= 100)
 {
 if(TempAirplane.moveGuid.flag != “runway”)
 {
 TempAirplane.moveGuid.flag = “terminal”;
 }
 currentPoint.move(380, 100);
 }
 }
 if(TempAirplane.flow_ID == 1) {
 delta = 4;
 if(currentPoint.x >=50 && currentPoint.x <250)
 currentPoint.translate(delta, 0);
 if(currentPoint.x >=250 && currentPoint.y <= 300
 && currentPoint.y > 100)
 currentPoint.translate(
 Math.round((float)Math.cos(Math.atan(200/50))*delta),
 -Math.round((float)Math.sin(Math.atan(200/50))*delta));
111
 if(currentPoint.y <= 100)
 currentPoint.move(300, 100);
 }
 }
}
class AirplaneFleet implements Runnable {
 static Thread runAirplane;
 Airplane tempAirplane;
 AirplaneFleet()
 {
 if(runAirplane == null){
 runAirplane = new Thread(this);
 runAirplane.start();
 }
 }
 public void startRun(){
 runAirplane = new Thread(this);
 runAirplane.start();
 }
 public void run(){
 while(true){
if(!AirplaneScheduler.AirplaneList.isEmpty())
 for(int i = 0; i < AirplaneScheduler.AirplaneList.size(); i++)
 {
 tempAirplane = (Airplane) AirplaneScheduler.AirplaneList.elementAt(i);
 tempAirplane.run();
 }
 try{runAirplane.sleep(30);} catch (Exception e) {};
 }
 }
}
class SpeedController extends Object {
 public int travelDistance;
 double deltaXSqt, deltaYSqt;
 Vector timeSpaceTable = new Vector(100);
 long deltaTime;
 long startTime;
 long finishTime;
 int clockInterval;
 String flag = new String(“no flag”);
 Point from = new Point(0,0);
 Point to = new Point(0,0);
 int prevTotal = 0;
 int n_segs = 30;
 SpeedController(int Interval) {clockInterval = Interval;}
 public void setSpeedProfile(Point From, Point To, long StartTime, long FinishTime)
 {
 deltaXSqt = Math.pow((double)(From.x -To.x), 2);
 deltaYSqt = Math.pow((double)(From.y -To.y), 2);
 System.out.println(“from:” + From.x + “:” + To.x
 + “time: “ + StartTime + “-> “+ FinishTime);
 travelDistance = (int) Math.round(Math.sqrt(deltaXSqt + deltaYSqt));
 deltaTime = FinishTime - StartTime;
 this.startTime = StartTime;
 this.finishTime = FinishTime;
 from.x = From.x; from.y = From.y;
 to.x = To.x; to.y = To.y;
 }
 int getDistance ( Point From, Point To)
 {
 double deltaX = Math.pow((double)(From.x -To.x), 2);
112
 double deltaY = Math.pow((double)(From.y - To.y), 2);
 int distance = (int) Math.round(Math.sqrt(deltaXSqt + deltaYSqt));
 return distance;
 }
 public int getSpeedProfile(Point CurrentPoint, long FutureTime)
 {
 int total = (int)((((long)travelDistance)/deltaTime)*(FutureTime - startTime));
 int distance = total - prevTotal;
 prevTotal = total;
 return Math.min(travelDistance, distance);
 }
 public int getFastMoveStep(long TimeLimit, int AirplaneTick){
 return (int) Math.round(travelDistance/TimeLimit);
 }
 public void setTimeSpaceTable(Point from, Point to,
 long startTime, int deltaTime)
 {
 if(from.x == to.x)
 {
 int length = to.y - from.y;
 double delta = length/n_segs;
 timeSpaceTable.removeAllElements();
 for(int i = 0; i < n_segs; i++)
 {
 if(i < n_segs - 1) {
 timeSpaceTable.addElement(
 new MovingRecord((long)(startTime + (long)i*deltaTime/n_segs),
 new Point(from.x, (int) Math.round(i*delta) + from.y)));
 }
 else
 {
 timeSpaceTable.addElement(
 new MovingRecord((long)(startTime + i),
 new Point(to.x, to.y)));
 }
 }
 return;
 }
 if(from.y == to.y)
 {
 int length = to.x - from.x;
 double delta = length/n_segs;
 timeSpaceTable.removeAllElements();
 for(int i = 0; i < n_segs; i++)
 {
 if(i < n_segs-1) {
 timeSpaceTable.addElement(
 new MovingRecord((long)(startTime + (long)i*deltaTime/n_segs),
 new Point((int) Math.round(i*delta)+from.x, to.y)));
 }
 else
 {
 timeSpaceTable.addElement(
 new MovingRecord((long)(startTime + i),
 new Point(to.x, to.y)));
 }
 }
 return;
 }
 }
 public Point getNextCoordinate(long timeAt) {
113
 int counter =0;
 MovingRecord tempRecord;
 while(counter < timeSpaceTable.size())
 {
 tempRecord =(MovingRecord)timeSpaceTable.elementAt(counter);
 if(tempRecord.timeAt >= timeAt )
 return tempRecord.xyCoordination;
 counter++;
 }
 return
 ((MovingRecord)timeSpaceTable.elementAt(
 timeSpaceTable.size() - 1)).xyCoordination; // always return last point
 }
}
class MovingRecord extends Object
{
 Point xyCoordination;
 long timeAt = 0;
 MovingRecord(long time, Point At)
 {
 timeAt = time;
 xyCoordination = new Point(At.x, At.y);
 // System.out.println(“create:”+ At.x + “,” + At.y);
 }
 }
class AirplaneDataSet
 {
 public boolean equals(AirplaneDataSet temp_data)
 {
 if(this.name.equals(temp_data.name))
 return true;
 else
 return false;
 }
 public AirplaneDataSet(String name, String category)
 {
 this.name = new String(name);
 this.category = new String(category);
 }
 public String category;
 public String name;
 }
import java.awt.*;
import java.util.*;
public class AirplaneScheduler implements Runnable {
 static Vector AirplaneList;
 static Vector ArrivalRates;
 static Thread CreateFlightThread;
 GlobalClock Watch = new GlobalClock();
 int delta = 120;
 int prevTime = 0;
long currentTime;
 AirplaneScheduler() {
 // System.out.println(“start initiating Scheduler”);
 startScheduler();
 // System.out.println(“finished initiating Scheduler”);
 }
 public synchronized void startScheduler() {
System.out.println(“here”);
AirplaneList = null;
ArrivalRates = null;
114
AirplaneList = new Vector(100);
 ArrivalRates = new Vector();
 addRandomSchedule(0, 120);
 addRandomSchedule(1, 60);
 CreateFlightThread = new Thread(this);
 CreateFlightThread.start();
 }
 public void createAirplane(int Path) {
 // AirplaneList.addElement(new Airplane(Path, 50, 300));
 AirplaneList.addElement(new Airplane(Path));
 }
 // add random schedule for the Path
 public void addRandomSchedule(int Path, int ArrivalRate) {
 ArrivalRates.addElement(
 (new ArrivalGenerator(Path, ArrivalRate)));
 }
 public synchronized void run() {
 while(true) {
 // control the total simulated airplane fleet size
 if(AirplaneList.size() < 200)
 {
 //get current time
 currentTime = (long) Watch.getTime();
 //create arrival for each path
 for(int i = 0; i < ArrivalRates.size(); i++)
 {
 //System.out.println(“Arrival Path size: “ + ArrivalRates.size());
 ArrivalGenerator newRate =
 (ArrivalGenerator) ArrivalRates.elementAt(i);
 //check if the interarrival interval reached
 if(newRate != null &&
 newRate.nextTime <= currentTime)
 {
 // create time mark for next arrival
 newRate.setTimeMarkForNextArrival((int)currentTime);
 // System.out.println(“Create ACFT at: “ + currentTime);
 // create this arrival
 createAirplane(newRate.path);
 }
 newRate = null;
 }
 try{CreateFlightThread.sleep(50);} catch (Exception e) {};
 }
 }
 }
}
class ArrivalGenerator extends Object{
 int path;
 int averageInterval;
 int nextTime;
 int minInterval;
 ArrivalGenerator(int Path, int AverageInterval) {
 minInterval = 20;
 this.path = Path;
 this.averageInterval = AverageInterval - minInterval;
 this.nextTime = randomInterval();
 }
 int randomInterval(){
 return( (int)(Math.floor(Math.random()*averageInterval))
 + minInterval);
 }
115
 public void setTimeMarkForNextArrival(int currentTime) {
 nextTime = currentTime + randomInterval();
//System.out.println(nextTime);
 }
}
public final class ControlLogic extends Object
 {
 public static int getCategoryByAircraft(Airplane airplane)
 {
 double tempCat = Math.random()*100;
 double sum_1 = 0, sum_2 = 0;
 String tempAcName;
 for(int i = 0; i < TrafficParam.AIRCRAFT_MIX.size(); i++)
 {
 if(i > 0)
 sum_1 +=((Double)TrafficParam.AIRCRAFT_MIX.elementAt(i-1)).doubleValue();
 sum_2 +=((Double)TrafficParam.AIRCRAFT_MIX.elementAt(i)).doubleValue();
 if(tempCat >= sum_1 && tempCat < sum_2)
 {
 tempAcName = (String) TrafficParam.AIRCRAFT_NAME.elementAt(i);
 airplane.setAircraftName(tempAcName);
 airplane.setAirplaneCategory(TrafficParam.getCategoryByAircraft(tempAcName));
 if(airplane.isCategory(“CAT_A”))
 return 1;
 if(airplane.isCategory(“CAT_B”))
 return 2;
 if(airplane.isCategory(“CAT_C”))
 return 3;
 if(airplane.isCategory(“CAT_D”))
 return 4;
 }
 }
 return 0;
 }
 public static long getTravelTime(String queue_name, Airplane airplane)
 {
 long time;
 if(airplane.CurrentQueue.name.equals(“16_R”) ||
 airplane.CurrentQueue.name.equals(“16_L”)){
 time = TrafficParam.getROT(queue_name, airplane);
 // System.out.println(“queue: “+queue_name+”time: “+time);
 }
 else
 time = TrafficParam.getTaxiwayTravelTime(queue_name, airplane);
 return time;
 }
 public static int getCategory()
 {
 double tempCat = Math.random();
 double sum_1 = 0, sum_2 = 0;
 for(int i = 0; i < TrafficParam.CATEGORY_MIX.size(); i++)
 {
 if(i > 0)
 sum_1 +=((Double)TrafficParam.CATEGORY_MIX.elementAt(i-1)).doubleValue();
 sum_2 +=((Double)TrafficParam.CATEGORY_MIX.elementAt(i)).doubleValue();
 if(tempCat >= sum_1 && tempCat < sum_2)
 return i+1;
 }
116
 return 0;
 }
 public static int getNextAvailableExitIdForCat_D()
 {
 String tempQname;
 for(int i = 3; i < TrafficParam.EXIT_NAME.size(); i++)
 {
 tempQname = (String) TrafficParam.EXIT_NAME.elementAt(i);
 if(GroundMonitor.airplaneQueueIsEmpty(tempQname))
 return i+1; // indx starting with 0
 }
 return 6;
 }
 public static int getNextAvailableExitIdForCat_C()
 {
 String tempQname;
 for(int i = 1; i < TrafficParam.EXIT_NAME.size(); i++)
 {
 tempQname = (String) TrafficParam.EXIT_NAME.elementAt(i);
 if(GroundMonitor.airplaneQueueIsEmpty(tempQname))
 return i+1; // indx starting with 0
 }
 return 6;
 }
 public static int getNextAvailableExitIdForCat_B()
 {
 String tempQname;
 for(int i = 1; i < TrafficParam.EXIT_NAME.size(); i++)
 {
 tempQname = (String) TrafficParam.EXIT_NAME.elementAt(i);
 if(GroundMonitor.airplaneQueueIsEmpty(tempQname))
 return i+1; // indx starting with 0
 }
 return 6;
 }
 public static int getNextAvailableExitIdForCat_A()
 {
 String tempQname;
 for(int i = 0; i < TrafficParam.EXIT_NAME.size(); i++)
 {
 tempQname = (String) TrafficParam.EXIT_NAME.elementAt(i);
 if(GroundMonitor.airplaneQueueIsEmpty(tempQname))
 return i+1; // indx starting with 0
 }
 return 6;
 }
 public static void setTrafficLightGreen(String queue_name)
 {
 ((AirplaneQueue)
 GroundMonitor.getAirplaneQueueObject(queue_name)
 ).TrafficSignal.setGreen();
 }
 public static void setTrafficLightRed(String queue_name)
 {
 ((AirplaneQueue)
 GroundMonitor.getAirplaneQueueObject(queue_name)
 ).TrafficSignal.setRed();
 }
 public static void controlAllAirplanes(Airplane airplane)
 {
 airplane.delayIndicator = false;
 if(airplane.flow_ID == 0)
 controlDepartAirplanes(airplane);
 else
 controlArriveAirplanes(airplane);
 }
 public static void controlDepartAirplanes(Airplane airplane)
117
 {
 getCategoryByAircraft(airplane);
 airplaneFollowPath_16_L_TAKEOFF(airplane);
 }
 public static void airplaneFollowPath_16_L_TAKEOFF(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_L_TAKEOFF”);
 airplane.RoutName = new String(“ENROUT”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(10);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_L”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_L
 if(airplane.NextQueue.name.equals(“16_L”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_3”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_4”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_5”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_6”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_7”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“C_8”)
 )
 {
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_L”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_34_R”);
 GroundMonitor.enterAirplaneIntoQueue(“16_L”, airplane);
 airplane.setTimer(TrafficParam.getROT(“16_L”,airplane));
 return;
 }
 }
 //enter runway C_6
 if(airplane.NextQueue.name.equals(“TERMINAL_34_R”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“16_L”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_34_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
118
 airplane.delayIndicator = true;
 return;
 }
 public static void airplaneFollowPath_16_R_TO_C_3(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_3”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_3”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.setTimer(getTravelTime(“C_3”, airplane));
 airplane.setStateToLand();
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_6
 if(airplane.NextQueue.name.equals(“C_3”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_3”))
 {
 setTrafficLightGreen(“C_3”);
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_2”);
 GroundMonitor.enterAirplaneIntoQueue(“C_3”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_3");
 airplane.setTimer(getTravelTime(“C_3”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 else
 setTrafficLightRed(“C_3”);
 }
 if(airplane.NextQueue.name.equals(“B_2”))
119
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_2”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_2”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_3”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_3");
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_2”);
 GroundMonitor.enterAirplaneIntoQueue(“B_2”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_2");
 airplane.setTimer(getTravelTime(“B_2”, airplane));
 return;
 }
 else
 setTrafficLightRed(“B_2”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_2”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_2”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_2”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
 airplane.delayIndicator = true;
 return;
 }
 public static void airplaneFollowPath_16_R_TO_C_4(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_4”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
120
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_4”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.setTimer(getTravelTime(“C_4”, airplane));
 airplane.setStateToLand();
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_6
 if(airplane.NextQueue.name.equals(“C_4”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_4”))
 {
 setTrafficLightGreen(“C_4”);
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_7”);
 GroundMonitor.enterAirplaneIntoQueue(“C_4”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_8");
 airplane.setTimer(getTravelTime(“C_4”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 setTrafficLightRed(“C_4”);
 }
 if(airplane.NextQueue.name.equals(“B_7”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_7”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_7”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_4”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_7”);
 GroundMonitor.enterAirplaneIntoQueue(“B_7”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_11");
 airplane.setTimer(getTravelTime(“B_7”, airplane));
 return;
 }
 else
 setTrafficLightRed(“B_7”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_7”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_7”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
121
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_7”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
 airplane.delayIndicator = true;
 return;
 }
 public static void setDynamicControl()
 {
 controlMethod = DYNAMIC_CONTROL;
 }
 public final static int DYNAMIC_CONTROL = 1;
 public final static int STATIC_CONTROL = 0;
 public static void setStaticControl()
 {
 controlMethod = STATIC_CONTROL;
 }
 static int controlMethod;
 public static void airplaneFollowPath_16_R_TO_C_5(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_5”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_5”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.setTimer(getTravelTime(“C_5”, airplane));
 airplane.setStateToLand();
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_6
 if(airplane.NextQueue.name.equals(“C_5”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_5”))
 {
122
 setTrafficLightGreen(“C_5”);
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_9”);
 GroundMonitor.enterAirplaneIntoQueue(“C_5”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_8");
 airplane.setTimer(getTravelTime(“C_5”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 else
 setTrafficLightRed(“C_5”);
 }
 if(airplane.NextQueue.name.equals(“B_9”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_9”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_9”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_5”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_9”);
 GroundMonitor.enterAirplaneIntoQueue(“B_9”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_11");
 airplane.setTimer(getTravelTime(“B_9”, airplane));
 return;
 }
 else
 setTrafficLightRed(“B_9”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_9”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_9”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_9”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
 airplane.delayIndicator = true;
 return;
 }
 public static void airplaneFollowPath_16_R_TO_C_6(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_6”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
123
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_6”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.setTimer(getTravelTime(“C_6”, airplane));
 airplane.setStateToLand();
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_6
 if(airplane.NextQueue.name.equals(“C_6”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_6”))
 {
 setTrafficLightGreen(“C_6”);
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_10”);
 GroundMonitor.enterAirplaneIntoQueue(“C_6”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_8");
 airplane.setTimer(getTravelTime(“C_6”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 else
 setTrafficLightRed(“C_6”);
 }
 if(airplane.NextQueue.name.equals(“B_10”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_10”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_10”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_6”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
124
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_10”);
 GroundMonitor.enterAirplaneIntoQueue(“B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_11");
 airplane.setTimer(getTravelTime(“B_10”, airplane));
 return;
 }
 else
 setTrafficLightRed(“B_10”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_10”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
 airplane.delayIndicator = true;
 return;
 }
 public static void airplaneFollowPath_16_R_TO_C_7(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_7”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_7”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.setTimer(getTravelTime(“C_7”, airplane));
 airplane.setStateToLand();
 return;
 }
125
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_8
 if(airplane.NextQueue.name.equals(“C_7”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_7”))
 {
 setTrafficLightGreen(“C_7”);
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_10”);
 GroundMonitor.enterAirplaneIntoQueue(“C_7”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_8");
 airplane.setTimer(getTravelTime(“C_7”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 else
 setTrafficLightRed(“C_7”);
 }
 if(airplane.NextQueue.name.equals(“B_10”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_10”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_10”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_7”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_10”);
 GroundMonitor.enterAirplaneIntoQueue(“B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_11");
 airplane.setTimer(getTravelTime(“B_10”, airplane));
 return;
 }
 else
 setTrafficLightRed(“B_10”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_10”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_10”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
126
 }
 airplane.delayIndicator = true;
 return;
 }
 public static void airplaneFollowPath_16_R_TO_C_8(Airplane airplane)
 {
 if(airplane.pathName == null)
 {
 airplane.pathName = new String(“16_R_TO_C_8”);
 // System.out.println(“set Path name from airplane” + airplane.ID());
 }
 if(airplane.getTimer() == 0)
 {
 airplane.setTimer(120);
 // System.out.println(“set timer in approach for airplane: “ + airplane.ID());
// airplane.CurrentQueue = null;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“16_R”);
 return;
 }
 //enter runway 16_R
 if(airplane.NextQueue.name.equals(“16_R”))
 {
 if(airplane.timerTimeOut() &&
 GroundMonitor.airplaneQueueIsEmpty(“16_R”))
 {
 setTrafficLightGreen(“16_R”);
 // System.out.println(“Airplane: “ + airplane.ID() +”enter runway 16_R”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“C_8”);
 GroundMonitor.enterAirplaneIntoQueue(“16_R”, airplane);
 airplane.setTimer(getTravelTime(“C_8”, airplane));
 airplane.setStateToLand();
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 //enter runway C_8
 if(airplane.NextQueue.name.equals(“C_8”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“C_8”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“16_R”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” leaves runway 16_R”);
 setTrafficLightGreen(“C_8”);
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“C_8”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +” enter exit C_8");
 airplane.setTimer(getTravelTime(“C_8”, airplane));
 airplane.setStateToTaxi();
 return;
 }
 else
 setTrafficLightRed(“C_8”);
127
 }
 if(airplane.NextQueue.name.equals(“B_11”))
 {
 if(GroundMonitor.airplaneQueueIsEmpty(“B_11”)
 &&
 GroundMonitor.airplaneQueueIsEmpty(“16_L”))
 {
 setTrafficLightGreen(“B_11”);
 GroundMonitor.moveAirplaneOutFromQueue(“C_8”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 airplane.NextQueue =
 GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“B_11”, airplane);
 //System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway B_11");
 airplane.setTimer(getTravelTime(“B_11”, airplane));
 return;
 }
 else
 setTrafficLightRed(“16_R”);
 }
 if(airplane.NextQueue.name.equals(“TERMINAL_B_11”))
 {
 GroundMonitor.moveAirplaneOutFromQueue(“B_11”, airplane);
 // System.out.println(“Airplane: “ + airplane.ID() +”leaves exit C_8");
 airplane.CurrentQueue = airplane.NextQueue;
 // GroundMonitor.getAirplaneQueueObject(“TERMINAL_B_11”);
 GroundMonitor.enterAirplaneIntoQueue(“TERMINAL_B_11”, airplane);
// System.out.println(“Airplane: “ + airplane.ID() +”enter taxiway TERMINAL_B_11");
 airplane.setAirplaneFinalStop();
 return;
 }
 airplane.delayIndicator = true;
 return;
 }
 //customized design for specific ground network configuration
 public static void controlArriveAirplanes(Airplane airplane)
 {
 setDynamicControl();
 if(controlMethod == DYNAMIC_CONTROL && airplane.pathName == null)
 {
 airplane.RoutName = new String(“ARRIVE”);
 // int cat =getCategory();
 int cat = getCategoryByAircraft(airplane);
 switch(cat) {
 case 1:
 airplane.flow_ID = getNextAvailableExitIdForCat_A();
 break;
 case 2:
 airplane.flow_ID = getNextAvailableExitIdForCat_B();
 break;
128
 case 3:
 airplane.flow_ID = getNextAvailableExitIdForCat_C();
 break;
 case 4:
 airplane.flow_ID = getNextAvailableExitIdForCat_D();
 break;
 default:
 airplane.flow_ID = getNextAvailableExitIdForCat_B();
 break;
 }
 }
 switch(airplane.flow_ID) {
 case 6: //C_8
 airplaneFollowPath_16_R_TO_C_8(airplane);
 break;
 case 5: //C_7
 airplaneFollowPath_16_R_TO_C_7(airplane);
 break;
 case 4: //C_6
 airplaneFollowPath_16_R_TO_C_6(airplane);
 break;
 case 3: //C_5
 airplaneFollowPath_16_R_TO_C_5(airplane);
 break;
 case 2: //C_4
 airplaneFollowPath_16_R_TO_C_4(airplane);
 break;
 case 1://C_3
 airplaneFollowPath_16_R_TO_C_3(airplane);
 break;
 default:
 break;
 }
 }
 }
/* System.out.println(“enter controlArriveAirplane Path 0: “+
 GroundMonitor.queueHasAirplane(“16_R”) +
 “timer: “ + airplane.getTimer() +
 “time: “ + airplane.Watch.getTime());
*/
import java.awt.*;
public final class GlobalClock implements Runnable{
 private static long time_base;
 private static int delta;
 private static int hour;
 private static int minute;
 private static int second;
 private static int subsecond;
 public static final int interval = 500;
 Thread ClockThread;
 GlobalClock(int timeStep){
 delta = timeStep;
 time_base = 0;
 hour = 0;
 minute = 0;
 second = 0;
 subsecond = 0;
 ClockThread = new Thread(this);
 // (new Thread(this)).start();
 ClockThread.setPriority(ClockThread.MIN_PRIORITY);
 ClockThread.start();
129
 }
 GlobalClock(){};
public void startClock(){
 ClockThread = new Thread(this);
 ClockThread.start();
}
public void pause_clock(){
ClockThread.suspend();
}
public void resume_clock(){
ClockThread.resume();
}
 public long convertTime(int hour, int minute, int second) {
 if(hour < 0 || minute < 0 || second < 0)
 {
 System.out.println(“Wrong time format”);
 System.exit(1);
 }
 return (hour*3600 + minute * 60 +second * 60);
 }
 public long getTime() {
 return (this.time_base);
 }
 public int checkTime(long time) {
 if(time_base > time)
 return (1);
 if(time_base == time)
 return (0);
 if(time_base < time)
 return (-1);
 return (100);
 }
public void printTime(Graphics g, int x, int y){
g.drawString(“TIME: “+ hour+”:”+minute+”:”+second, x, y);
}
 public synchronized void run() {
 while(true) {
 // System.out.println(“Clock Advanced “+time_base);
 time_base = time_base+delta;
 /*
 subsecond +delta;
 if(subsecond >= 10){
 second++;
 subsecond -= 10;
 }
 */
 second += delta;
 if(second >= 60){
 minute++;
 second -= 60;
 }
 if(minute >= 60){
 hour++;
 minute -= 60;
 }
 if(hour >=24)
 System.exit(0);
 try{Thread.sleep(interval);} catch (Exception e) {};
130
 }
 }
}
import java.awt.*;
import java.util.*;
public final class GroundMonitor
{
 public static NetPath getNetPathObject(String path_name)
 {
 NetPath tempP;
 for(int i = 0; i < AllNetPaths.size(); i++)
 {
 tempP =(NetPath) AllNetPaths.elementAt(i);
 if(tempP.name.equals(path_name)){
 return tempP;
 }
 }
 System.out.println(“wrong path_name in getNetPathObject”);
 return null;
 }
 boolean DATA_INITIALIZED = false;
 static Vector queues = new Vector(30);
 public static Vector AllNetPaths = new Vector();
 public static void createAllNetPaths()
 {
 System.out.println(“Start createAllNetPath in Ground Monitor”);
 NetPath tempPath = new NetPath(“16_R_TO_C_3”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_3”, 1);
 tempPath.addLinksAt(“B_2”, 2);
 tempPath.addLinksAt(“TERMINAL_B_2”, 3);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 tempPath = new NetPath(“16_R_TO_C_4”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_4”, 1);
 tempPath.addLinksAt(“B_7”, 2);
 tempPath.addLinksAt(“TERMINAL_B_7”, 3);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 tempPath = new NetPath(“16_R_TO_C_5”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_5”, 1);
 tempPath.addLinksAt(“B_9”, 2);
 tempPath.addLinksAt(“TERMINAL_B_9”, 3);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 tempPath = new NetPath(“16_R_TO_C_6”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_6”, 1);
 tempPath.addLinksAt(“B_10”, 2);
 tempPath.addLinksAt(“TERMINAL_B_10”, 3);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 tempPath = new NetPath(“16_R_TO_C_7”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_7”, 1);
 tempPath.addLinksAt(“B_10”, 2);
 AllNetPaths.addElement(tempPath);
 tempPath.addLinksAt(“TERMINAL_B_10”, 3);
 tempPath = null;
131
 tempPath = new NetPath(“16_R_TO_C_8”);
 tempPath.addLinksAt(“16_R”, 0);
 tempPath.addLinksAt(“C_8”, 1);
 tempPath.addLinksAt(“B_11”, 2);
 tempPath.addLinksAt(“TERMINAL_B_11”, 3);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 tempPath = new NetPath(“16_L_TAKEOFF”);
 tempPath.addLinksAt(“16_L”, 0);
 tempPath.addLinksAt(“TERMINAL_34_R”, 1);
 AllNetPaths.addElement(tempPath);
 tempPath = null;
 System.out.println(“end create AllNetPath in Ground Monitor”);
 }
 // get next airplanequeue object given the path name and the
 // current queue name
 public static synchronized AirplaneQueue getNextAirplaneQueueObject
 (String path_name, String current_queue)
 {
 NetPath tempP = getNetPathObject(path_name);
 if(tempP != null)
 return tempP.getNextLink(current_queue);
 System.out.println(“wrong path_name in getNextAirplaneQueueObject”);
 return null;
 }
 //check wether an airplanequeue is empty
 public static boolean airplaneQueueIsEmpty(String queue_name)
 {
 if(queueHasAirplane(queue_name) > 0)
 return false;
 else
 return true;
 }
 //get an airplanequeue object given airplanequeue name
 public static synchronized AirplaneQueue getAirplaneQueueObject(String queue_name)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++)
 {
 tempQ =(AirplaneQueue) queues.elementAt(i);
 if(tempQ.name.equals(queue_name)){
 // System.out.println(“GetAirplaneQueueName:” + tempQ.name);
 return (AirplaneQueue) tempQ;
 }
 // System.out.println(“AirplaneQueueName:” + tempQ.name + “target name” +
queue_name);
 }
 System.out.println(“wrong queue_name in getAirplaneQueueObject”);
 return null;
 }
 //get the first airplane in a given airplane queue
 public Airplane theFirstAirplane(String queue_name)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++)
 {
 tempQ =(AirplaneQueue) queues.elementAt(i);
132
 if(tempQ.name == queue_name){
 return (Airplane) tempQ.queue.firstElement();
 }
 }
 System.out.println(“wrong AirplaneQueue name; program terminated 4”);
 return null;
 }
 //get the last airplane from the given airplane queue
 //without remove the airplane
 public Airplane theLastAirplane(String queue_name)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++)
 {
 tempQ =(AirplaneQueue) queues.elementAt(i);
 if(tempQ.name == queue_name){
 return (Airplane) tempQ.queue.lastElement();
 }
 }
 System.out.println(“wrong AirplaneQueue name; program terminated 1”);
 return null;
 }
 //check if the airplane queue has airplane
 public static synchronized int queueHasAirplane(String queue_name)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++)
 {
 tempQ =(AirplaneQueue) queues.elementAt(i);
 if(tempQ.name.equals(queue_name)){
 return tempQ.queue.size();
 }
 }
 System.out.println(“wrong AirplaneQueue name; program terminated 2”);
 return (0);
 }
 // create all of the airplanequeues
 public static void createAllQueues(){
 System.out.println(“Start creating ground liks”);
 queues.addElement(new AirplaneQueue(“16_R”, new Point(85,223), new Point(439,223)));
 queues.addElement(new AirplaneQueue(“16_L”, new Point(85,195), new Point(514,195)));
 queues.addElement(new AirplaneQueue(“A_1”, new Point(85,177), new Point(85,162)));
 queues.addElement(new AirplaneQueue(“A_2”, new Point(85,162), new Point(236,162)));
 queues.addElement(new AirplaneQueue(“C_1”, new Point(85,223), new Point(85,195)));
 queues.addElement(new AirplaneQueue(“C_2”, new Point(85,223), new Point(439,223)));//
not implemented
 queues.addElement(new AirplaneQueue(“C_3”, new Point(171,223), new Point(208,195)));
 queues.addElement(new AirplaneQueue(“C_4”, new Point(270,223), new Point(341,195)));
 queues.addElement(new AirplaneQueue(“C_5”, new Point(300,223), new Point(364,195)));
 queues.addElement(new AirplaneQueue(“C_6”, new Point(375,223), new Point(418,195)));
 queues.addElement(new AirplaneQueue(“C_7”, new Point(418,223), new Point(418,195)));
 queues.addElement(new AirplaneQueue(“C_8”, new Point(439,223), new Point(439,195)));
 queues.addElement(new AirplaneQueue(“B_1”, new Point(85,195), new Point(85,177)));
 queues.addElement(new AirplaneQueue(“B_2”, new Point(208,195), new Point(236,177)));
 queues.addElement(new AirplaneQueue(“B_3”, new Point(85,223), new Point(439,223))); //
not implemented
 queues.addElement(new AirplaneQueue(“B_4”, new Point(85,223), new Point(439,223))); //
not implemented
 queues.addElement(new AirplaneQueue(“B_5”, new Point(85,223), new Point(439,223)));//
 queues.addElement(new AirplaneQueue(“B_6”, new Point(85,223), new Point(439,223)));//
 queues.addElement(new AirplaneQueue(“B_7”, new Point(341,195), new Point(330,177)));
 queues.addElement(new AirplaneQueue(“B_9”, new Point(364,195), new Point(375,177)));
 queues.addElement(new AirplaneQueue(“B_10”, new Point(418,195), new Point(418,177)));
 queues.addElement(new AirplaneQueue(“B_11”, new Point(439,195), new Point(439,177)));
 queues.addElement(new AirplaneQueue(“TERMINAL_B_2”, new Point(236,177), new
Point(236,177)));
 queues.addElement(new AirplaneQueue(“TERMINAL_B_7”, new Point(330,177), new
Point(330,177)));
133
 queues.addElement(new AirplaneQueue(“TERMINAL_B_9”, new Point(375,177), new
Point(375,177)));
 queues.addElement(new AirplaneQueue(“TERMINAL_B_10”, new Point(418,177), new
Point(418,177)));
 queues.addElement(new AirplaneQueue(“TERMINAL_B_11”, new Point(439,177), new
Point(439,177)));
 queues.addElement(new AirplaneQueue(“TERMINAL_34_R”, new Point(514,195), new
Point(514,195)));
 queues.addElement(new AirplaneQueue(“TERMINAL_A_2”, new Point(236,162), new
Point(236,162)));
 System.out.println(“finish creating ground links”);
 }
 //add an airplane into the airplane queue
 public static boolean enterAirplaneIntoQueue(String queue_name, Airplane airplane)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++)
 {
 tempQ =(AirplaneQueue) queues.elementAt(i);
 if(tempQ.name.equals(queue_name)){
 tempQ.enterAirplane(airplane);
 return true;
 }
 }
 return false;
 }
 //remove an airplane out of a given airplane queue
 public static synchronized boolean moveAirplaneOutFromQueue(String queue_name, Airplane
airplane)
 {
 AirplaneQueue tempQ;
 for(int i = 0; i < queues.size(); i++){
 tempQ =(AirplaneQueue) queues.elementAt(i);
 if(tempQ.name.equals(queue_name)){
 tempQ.departAirplane(airplane);
 return true;
 }
 }
 return false;
 }
}
import java.util.*;
import java.awt.*;
class AirplaneQueue {
 AirplaneQueue(String QueueName, Point from, Point to)
 {
 this.name = new String(QueueName);
 From = from;
 To = to;
 TrafficSignal = new TrafficLight();
 }
 public void setFromPointToPoint(int from_x, int from_y, int to_x, int to_y)
 {
 From = new Point(from_x, from_y);
 To = new Point(to_x, to_y);
 }
 public Point To;
 public Point From;
 public TrafficLight TrafficSignal;
 Point from;
134
 Point to;
 Vector queue = new Vector(10);
 String name;
 AirplaneQueue(String QueueName)
 {
 this.name = new String(QueueName);
 }
 public boolean equals(AirplaneQueue airplane_queue)
 {
 if(this.name.equals(airplane_queue.name))
 return true;
 else
 return false;
 }
 public void unholdFirstAirplane()
 {
 TrafficSignal.setGreen();
 }
 public void holdFirstAirplane()
 {
 TrafficSignal.setRed();
 }
 public void enterAirplane(Airplane airplane)
 {
 queue.addElement(airplane);
 //set airplane’s current track for animation
 airplane.updateTrack();
 }
 public void departAirplane(Airplane airplane){
// if(TrafficSignal.isGreen())
 queue.removeElement(airplane);
 }
 public void departAirplane()
 {
 // if(TrafficSignal.isGreen())
 queue.removeElementAt(0);
 }
}
class TrafficLight
 {
 public TrafficLight()
 {
 signal = green;
 }
 public static final int green = 2;
 public static final int yellow = 1;
 public static final int red = 0;
 private int signal;
 public synchronized boolean isGreen()
 {
 return (signal == green? true:false);
 }
 public synchronized boolean isYellow()
 {
 return (signal == this.yellow? true:false);
 }
 public synchronized boolean isRed()
 {
 return (signal == red? true:false);
 }
 public synchronized void setGreen()
 {
 signal = this.green;
135
 }
 public synchronized void setYellow()
 {
 signal = this.yellow;
 }
 public synchronized void setRed()
 {
 signal = this.red;
 }
 }
class NetPath extends Object
 {
 public AirplaneQueue getFirstAirplaneQueue()
 {
 if(!Links.isEmpty())
 return (AirplaneQueue) Links.firstElement();
 else
 return null;
 }
 public AirplaneQueue getLastQueueInPath()
 {
 if(!Links.isEmpty())
 return (AirplaneQueue) Links.lastElement();
 else
 return null;
 }
 public String name;
 Vector Links;
 int number_links;
 public NetPath(String name)
 {
 this.name = new String(name);
 Links = new Vector();
 number_links = 0;
 }
 public boolean isLastAirplaneQueue(AirplaneQueue airplanequeue)
 {
 if(airplanequeue == (AirplaneQueue) Links.lastElement())
 return true;
 else
 return false;
 }
 public boolean hasAirplaneQueue(String queue_name)
 {
 if(Links.contains(GroundMonitor.getAirplaneQueueObject(queue_name)))
 return true;
 else{
 System.out.println(“wrong queue_name in hasAirplaneQueue”);
 return false;
 }
 }
 public void addLinksAt(String queue_name, int position)
 {
 AirplaneQueue TempQ = GroundMonitor.getAirplaneQueueObject(queue_name);
 if(TempQ != null && !Links.contains(TempQ)){
 Links.insertElementAt(TempQ, position);
 number_links = Links.size();
 }
 }
 public AirplaneQueue getNextLink(String queue_name)
 {
 AirplaneQueue CurrentQ = GroundMonitor.getAirplaneQueueObject(queue_name);
136
 if(CurrentQ != null){
 for(int i = 0; i < Links.size(); i++)
 if(CurrentQ == (AirplaneQueue) Links.elementAt(i) && i < Links.size() - 1)
 return (AirplaneQueue) Links.elementAt(i+1);
 else
 return null;
 }
 return null;
 }
 }
import java.util.*;
import java.awt.*;
class AirplaneQueue {
 AirplaneQueue(String QueueName, Point from, Point to)
 {
 this.name = new String(QueueName);
 From = from;
 To = to;
 TrafficSignal = new TrafficLight();
 }
 public void setFromPointToPoint(int from_x, int from_y, int to_x, int to_y)
 {
 From = new Point(from_x, from_y);
 To = new Point(to_x, to_y);
 }
 public Point To;
 public Point From;
 public TrafficLight TrafficSignal;
 Point from;
 Point to;
 Vector queue = new Vector(10);
 String name;
 AirplaneQueue(String QueueName)
 {
 this.name = new String(QueueName);
 }
 public boolean equals(AirplaneQueue airplane_queue)
 {
 if(this.name.equals(airplane_queue.name))
 return true;
 else
 return false;
 }
 public void unholdFirstAirplane()
 {
 TrafficSignal.setGreen();
 }
 public void holdFirstAirplane()
 {
 TrafficSignal.setRed();
 }
 public void enterAirplane(Airplane airplane)
 {
 queue.addElement(airplane);
 //set airplane’s current track for animation
 airplane.updateTrack();
 }
 public void departAirplane(Airplane airplane){
// if(TrafficSignal.isGreen())
 queue.removeElement(airplane);
 }
137
 public void departAirplane()
 {
 // if(TrafficSignal.isGreen())
 queue.removeElementAt(0);
 }
}
class TrafficLight
 {
 public TrafficLight()
 {
 signal = green;
 }
 public static final int green = 2;
 public static final int yellow = 1;
 public static final int red = 0;
 private int signal;
 public synchronized boolean isGreen()
 {
 return (signal == green? true:false);
 }
 public synchronized boolean isYellow()
 {
 return (signal == this.yellow? true:false);
 }
 public synchronized boolean isRed()
 {
 return (signal == red? true:false);
 }
 public synchronized void setGreen()
 {
 signal = this.green;
 }
 public synchronized void setYellow()
 {
 signal = this.yellow;
 }
 public synchronized void setRed()
 {
 signal = this.red;
 }
 }
class NetPath extends Object
 {
 public AirplaneQueue getFirstAirplaneQueue()
 {
 if(!Links.isEmpty())
 return (AirplaneQueue) Links.firstElement();
 else
 return null;
 }
 public AirplaneQueue getLastQueueInPath()
 {
 if(!Links.isEmpty())
 return (AirplaneQueue) Links.lastElement();
 else
 return null;
 }
 public String name;
 Vector Links;
 int number_links;
 public NetPath(String name)
 {
 this.name = new String(name);
 Links = new Vector();
138
 number_links = 0;
 }
 public boolean isLastAirplaneQueue(AirplaneQueue airplanequeue)
 {
 if(airplanequeue == (AirplaneQueue) Links.lastElement())
 return true;
 else
 return false;
 }
 public boolean hasAirplaneQueue(String queue_name)
 {
 if(Links.contains(GroundMonitor.getAirplaneQueueObject(queue_name)))
 return true;
 else{
 System.out.println(“wrong queue_name in hasAirplaneQueue”);
 return false;
 }
 }
 public void addLinksAt(String queue_name, int position)
 {
 AirplaneQueue TempQ = GroundMonitor.getAirplaneQueueObject(queue_name);
 if(TempQ != null && !Links.contains(TempQ)){
 Links.insertElementAt(TempQ, position);
 number_links = Links.size();
 }
 }
 public AirplaneQueue getNextLink(String queue_name)
 {
 AirplaneQueue CurrentQ = GroundMonitor.getAirplaneQueueObject(queue_name);
 if(CurrentQ != null){
 for(int i = 0; i < Links.size(); i++)
 if(CurrentQ == (AirplaneQueue) Links.elementAt(i) && i < Links.size() - 1)
 return (AirplaneQueue) Links.elementAt(i+1);
 else
 return null;
 }
 return null;
 }
 }
import java.awt.*;
import java.util.*;
class LineObject
 {
 LineObject(Point from, Point to)
 {
 Track = new Vector(30);
 setFromPointToPoint((new Point(from.x, from.y)),
 (new Point(to.x, to.y)));
 }
 public double tangent;
 public void setTrack(int time)
 {
 int step;
 if(time <=0)
 delta = length;
 else
 delta = (int)(length/time);
 if(To.y == From.y)
 step = 30;
 else
 step = 5;
139
 if(From != null && To != null){
 for(int i = 0; i < step; i++)
 {
 Point tempMidPoint= new Point(
 (int)((To.x - From.x)/step*i + From.x),
 (int)((To.y - From.y)/step*i + From.y));
 Track.addElement(tempMidPoint);
 }
 Track.addElement(To);
 }
/*
 // horizontal line;
 // this will also handle situation when From.equals(To)== true;
 if(From.y == To.y)
 {
 for(i = 0; i < 9; i++)
 {
 delta *= (To.x > From.x? 1:(-1));
 Track.addElement(new Point(From.x + delta*i, From.y));
 }
 // System.out.println(“create track for: “+From.x+”,”
 // +From.y+”-> “+To.x+”,”+To.y);
 //last point is To
 Track.addElement(new Point(To.x, To.y));
 return;
 }
 //virtical line
 if(From.x == To.x)
 {
 for(i = 0; i < 9; i++)
 {
 delta *= (To.y > From.y? 1:-1);
 Track.addElement(new Point(From.x, From.y + delta*i));
 }
 //last point is To.
 Track.addElement(new Point(To.x, To.y));
 return;
 }
 //has a tangent
 tangent = (To.y - From.y)/(To.x - From.x);
 for(i = 0; i < 9; i++)
 {
 Track.addElement(new Point(From.x + (int)(delta*i/tangent),
 From.y + (int)(delta*i*tangent)));
 }
 //last poit is To.
 Track.addElement(new Point(To.x, To.y));
*/
 return;
 }
 public void setFromPointToPoint(Point from, Point to)
 {
 From = from;
 To = to;
 if(From == null || To == null){
 System.out.println(“setFromPointToPoint in LineObject: null Points”);
 System.exit(2);
 }
 length = (int) Math.sqrt((from.x - to.x)^2+(from.y - to.y)^2);
140
 }
 LineObject(int from_x, int from_y, int to_x, int to_y)
 {
 Track = new Vector(30);
 setFromPointToPoint((new Point(from_x, from_y)),
 (new Point(to_x, to_y)));
 }
 public Vector Track;
 public int delta;
 public int length;
 public Point To;
 public Point From;
 }
import java.util.*;
public final class TrafficParam
 {
 public static String getCategoryByAircraft(String ac_name)
 {
 AirplaneDataSet tempData;
 for(int i = 0; i < MASTER_ACFT_DATA.size(); i++){
 tempData = (AirplaneDataSet) MASTER_ACFT_DATA.elementAt(i);
 if(tempData.name.equals(ac_name))
 return tempData.category;
 }
 return null;
 }
 public static void insertAircraftMasterData(String name, String category)
 {
 AirplaneDataSet tempData = new AirplaneDataSet(name, category);
 if(!MASTER_ACFT_DATA.contains(tempData))
 MASTER_ACFT_DATA.addElement(tempData);
 }
 public static Vector MASTER_ACFT_DATA;
 public static long getTaxiwayTravelTime(String taxiway_name, Airplane airplane)
 {
 if(airplane.RoutName.compareTo(“ARRIVE”) == 0)
 {
 for(int i = 0; i < TAXIWAY_NAME.size(); i++)
 {
 if(((String) TAXIWAY_NAME.elementAt(i)).equals(taxiway_name))
 return ((Long) TAXIWAY_TRAVEL_TIME.elementAt(i)).longValue();
 }
 }
 System.out.println(“AirplaneQueue name:”+taxiway_name);
 return -1;
 }
 public static void insertTaxiwayName(String taxiway_name, long travel_time)
 {
 if(!TAXIWAY_NAME.contains(taxiway_name)){
 TAXIWAY_NAME.addElement(new String(taxiway_name));
 TAXIWAY_TRAVEL_TIME.addElement(new Long(travel_time));
 }
 }
 public static Vector TAXIWAY_TRAVEL_TIME;
 public static Vector TAXIWAY_NAME;
 public static long getROT(String exit_name, Airplane airplane)
 {
 // System.out.println(“RoutNmae: “+ airplane.RoutName);
 if(airplane.RoutName.compareTo(“ARRIVE”) == 0)
 {
 for(int i = 0; i < EXIT_NAME.size(); i++)
 {
 if(((String) EXIT_NAME.elementAt(i)).equals(exit_name))
 return ((Long) ROT_BY_EXITS.elementAt(i)).longValue();
141
 }
 }
 if(exit_name.compareTo(“16_L”) == 0
 &&
 airplane.RoutName.compareTo(“ENROUT”) == 0)
 // hard coded for departure from 16_L
 return (long) 70;
 //wrong exit name or rout_name
 return (-1);
 }
 public static void setAllTrafficParameters()
 {
 //insert exit name
 insertExitName(“C_3”, 0.05, 35);
 insertExitName(“C_4”, 0.20, 30);
 insertExitName(“C_5”, 0.30, 38);
 insertExitName(“C_6”, 0.30, 65);
 insertExitName(“C_7”, 0.05, 70);
 insertExitName(“C_8”, 0.10, 75);
 //insert category name
 insertAirplaneCategoryName(“CATEGORY_A”, 0.05);
 insertAirplaneCategoryName(“CATEGORY_B”, 0.25);
 insertAirplaneCategoryName(“CATEGORY_C”, 0.40);
 insertAirplaneCategoryName(“CATEGORY_D”, 0.30);
 //insert aircraft name
 insertAircraftName(“PA_38_112”, 1.0);
 insertAircraftName(“CE_208”, 1.0);
 insertAircraftName(“CE_402C”, 4.0);
 insertAircraftName(“EMB_120”, 2.0);
 insertAircraftName(“SA_227”, 12.0);
 insertAircraftName(“BAe_31”, 10.2);
 insertAircraftName(“DHC_7”, 4.0);
 insertAircraftName(“CE_550”, 4.1);
 insertAircraftName(“A_300_600”, 1.4);
 insertAircraftName(“B_767_300”, 7.8);
 insertAircraftName(“B_727_200”, 19.0);
 insertAircraftName(“B_737_300”, 12.4);
 insertAircraftName(“MD_83”, 12.0);
 insertAircraftName(“B_747_200B”, 3.0);
 insertAircraftName(“L_1011”, 3.1);
 insertAircraftName(“DC_8_73”,3.0);
 checkParameterIntegrity(“AIRCRAFT”);
 checkParameterIntegrity(“CATEGORY”);
 insertTaxiwayName(“C_3”, 15);
 insertTaxiwayName(“C_4”, 15);
 insertTaxiwayName(“C_5”, 15);
 insertTaxiwayName(“C_6”, 16);
 insertTaxiwayName(“C_7”, 20);
 insertTaxiwayName(“C_8”, 20);
 insertTaxiwayName(“B_2”, 20);
 insertTaxiwayName(“B_7”, 20);
 insertTaxiwayName(“B_9”, 20);
 insertTaxiwayName(“B_10”, 20);
 insertTaxiwayName(“B_11”, 20);
 insertAircraftMasterData(“PA_38_112”, “CAT_B”);
 insertAircraftMasterData(“CE_208”, “CAT_B”);
 insertAircraftMasterData(“CE_402C”, “CAT_B”);
 insertAircraftMasterData(“EMB_120”, “CAT_B”);
 insertAircraftMasterData(“SA_227”, “CAT_B”);
 insertAircraftMasterData(“BAe_31”, “CAT_C”);
 insertAircraftMasterData(“DHC_7”, “CAT_B”);
142
 insertAircraftMasterData(“CE_550”, “CAT_B”);
 insertAircraftMasterData(“A_300_600”, “CAT_D”);
 insertAircraftMasterData(“B_767_300”, “CAT_D”);
 insertAircraftMasterData(“B_727_200”, “CAT_C”);
 insertAircraftMasterData(“B_737_300”, “CAT_C”);
 insertAircraftMasterData(“MD_83”, “CAT_C”);
 insertAircraftMasterData(“B_747_200B”, “CAT_D”);
 insertAircraftMasterData(“L_1011”, “CAT_D”);
 insertAircraftMasterData(“DC_8_73”,”CAT_D”);
 }
 public static void insertAircraftName(String airplane_name, double percent)
 {
 if(!AIRCRAFT_NAME.contains(airplane_name)){
 AIRCRAFT_NAME.addElement(new String(airplane_name));
 AIRCRAFT_MIX.addElement(new Double(percent));
 }
 }
 public static Vector AIRCRAFT_NAME;
 public static Vector CATEGORY_MIX;
 public static double doubleSum(Vector v)
 {
 double sum = 0;
 for(Enumeration e = v.elements();
 e.hasMoreElements();
 sum +=((Number)e.nextElement()).doubleValue());
 // System.out.println(“double_sum = “+sum);
 return sum;
 }
 public static Vector CATEGORY_NAME;
 public static void insertAirplaneCategoryName(String cat_name, double percent)
 {
 if(!CATEGORY_NAME.contains(cat_name)){
 CATEGORY_NAME.addElement(new String(cat_name));
 CATEGORY_MIX.addElement(new Double(percent));
 }
 }
 public static void insertExitName(String exit_name, double percent, long rot)
 {
 if(!EXIT_NAME.contains(exit_name)){
 EXIT_NAME.addElement(new String(exit_name));
 EXIT_USAGE.addElement(new Double(percent));
 ROT_BY_EXITS.addElement(new Long(rot));
 }
 }
 public static Vector EXIT_NAME;
 public static void checkParameterIntegrity(String type)
 {
 // System.out.println(“wrong exit usage total = “ + doubleSum(EXIT_USAGE));
 double sum = doubleSum(EXIT_USAGE);
 if(Math.abs(sum - 1.00) > 0.001)
 {
 System.out.println(“wrong exit usage total = “ + doubleSum(EXIT_USAGE));
 System.exit(4);
 }
 if(type.compareTo(“AIRCRAFT”) == 0)
 if(doubleSum(AIRCRAFT_MIX) != 100){
 System.out.println(“wrong aircraft mix total”);
 System.exit(4);
 }
 if(type.compareTo(“CATEGORY”) == 0)
 if(doubleSum(CATEGORY_MIX) != 1.00){
 System.out.println(“wrong category mix total”);
 System.exit(4);
 }

 }
 public static void setExitUsage(int exit, double percentage)
 {
 if(exit < 0 || exit > 5)
 System.exit(3);
// EXIT_USAGE[exit] = percentage;
 }
 public static int NUM_OF_CATEGORIES = 4;
 public static int NUM_OF_EXITS = 6;
 public static void initiateTrafficParaClass()
 {
 EXIT_NAME = new Vector();
 CATEGORY_NAME = new Vector();
 AIRCRAFT_NAME = new Vector();
 EXIT_USAGE = new Vector();
 AIRCRAFT_MIX = new Vector();
 CATEGORY_MIX = new Vector();
 ROT_BY_EXITS = new Vector();
 TAXIWAY_NAME = new Vector();
 TAXIWAY_TRAVEL_TIME = new Vector();
 MASTER_ACFT_DATA = new Vector();
 }
 public static Vector ROT_BY_EXITS;
 public static Vector EXIT_USAGE;
 public static Vector AIRCRAFT_MIX;
 }
