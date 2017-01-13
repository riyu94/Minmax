/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

  /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minmax;
import java.util.*;
import java.lang.*;
import java.io.*;
/**
 *
 * @author riyu20
 */
public class Minmax {
    public static int n; 
       public static int d; 
      public static int cst;
    public static String play;
     public static String opponent;
     public static String opp;
     public static  ArrayList<State> arrb=new ArrayList<State>();
     public static  ArrayList<State> arra=new ArrayList<State>();
     public static void main(String[] args) {
        
        Minmax h=new Minmax();
        h.tempmethod();
    }
     public void tempmethod()
    {
        int i=0,j=0,k=0,boardstart;
        int score=0;
        String mode;
       String player;
       List<String> lines = new ArrayList<String>();
       ArrayList<State> act=new ArrayList<State>();
       String line;
       try{

FileReader fileReader = new FileReader("input.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
bufferedReader.close();
n=Integer.parseInt(lines.get(0));
mode=lines.get(1);
player=lines.get(2);
play=player;
d=Integer.parseInt(lines.get(3));
boardstart=4+n;
String[] splitlist=new String[n];
String[][] cell=new String[n][n];
String[][] cloneboard=new String[n][n];
String[][] finalboard=new String[n][n];
char[] split=new char[n];
int t=0,p=0;
//player & opponent
if(player.equals("X"))
{
    opponent="O";
    opp="O";
}
else
{
    opponent="X";
    opp="X";
}

//cell main board
for(i=4;i<4+n;i++)
   {
    splitlist=lines.get(i).split(" ");
    for(k=0;k<n;k++)
        cell[i-4][k]=splitlist[k];
    }
for(i=0;i<n;i++)
{
for(k=0;k<n;k++)   
System.out.print(cell[i][k]+" ");
System.out.println();
}


//clone
for(i=boardstart;i<n+boardstart;i++)
{
    split=lines.get(i).toCharArray();
for(k=0;k<n;k++)
     if(split[k]==('.'))
        cloneboard[i-boardstart][k]=".";
     else if(split[k]==('X'))
          cloneboard[i-boardstart][k]="X";
     else
         cloneboard[i-boardstart][k]="O";
    }
for(i=0;i<n;i++)
{
for(k=0;k<n;k++)   
System.out.print(cloneboard[i][k]);
System.out.println();
}


//state initialize
State s=new State();
s.board=cloneboard;
State v=new State();
//act=getstates(player,s,cloneboard,cell,s.d);
  
//main cases
switch(mode)
{
    case "MINIMAX":
           
      v= minimax(player,s,cloneboard,cell,finalboard);
        break;
    case "ALPHABETA":break;
    case "COMPETITION":break;
}


        }
       
       catch (FileNotFoundException e) {
            System.out.println("Unable to open file ");
        }
catch (IOException e1) {
            System.out.println("Error reading file");
        }
}
     
      public class State
    {
    int cst;
    int depth;
    String[][] board=new String[n+1][n+1];
    public State()
    {
    board=null;
    cst=0;
    depth=0;
    }
    
    public State(int cst,String[][] board,int depth)
    {
      this.board=board;
      this.cst=cst;
      this.depth=depth;
    }
    
    }
      
      
      public State minimax(String player,State s,String[][] cloneboard,String[][] cell,String[][] finalboard)
    {
        int i;
        int temp=0,pos=0;
    State v=new State(cst,cloneboard,s.depth);
    v=max(player,s,cloneboard,cell,finalboard);
    System.out.println(v.cst);
    for(i=0;i<n;i++)
    if(i%2==0)
    {
        for(i=0;i<arrb.size();i++)
{  if(temp<arrb.get(i).cst)
    {   temp=arrb.get(i).cst;
        pos=i;
    }

}
       // System.out.println("MAX"+arrb.get(pos).cst);
//finalboard=arrb.get(pos).board;
    }
    
    printmatrix(finalboard);
    return v;
    }
      
      public State max(String player,State s,String[][] cloneboard,String[][] cell,String[][] finalboard)
     
      {
          int i;
          int temp=0,pos=0;
          
      State t=new State(s.cst,cloneboard,s.depth);
      ArrayList<State> act=new ArrayList<State>();
    // 
      if(t.depth==d)
      {
           
          return t;
      }
      t.cst=(int) Double.NEGATIVE_INFINITY;
      act=getstates(player,s,cloneboard,cell,d);
       for(i=0;i<act.size();i++)
    {
      //  System.out.println(t.cst);
        
        arrb.add(act.get(i));
    t.cst=Math.max(t.cst,min(player,act.get(i),act.get(i).board,cell,finalboard).cst);
    }
       for(i=0;i<arrb.size();i++)
{  if(temp<arrb.get(i).cst)
    {   temp=arrb.get(i).cst;
        pos=i;
    }
}
//System.out.println(pos);
//System.out.println(t.depth+" "+d);
//System.out.println("max"+arrb.get(pos).cst);
//System.out.println("max");
//printmatrix(arrb.get(pos).board);
       
       System.out.println(t.cst);
   
       
   
      return t;
      }
      
      
      public State min(String player,State s,String[][] cloneboard,String[][] cell,String[][] finalboard)
     
      {
          int i;
          int temp=0,pos=0;
       
      State v=new State(s.cst,cloneboard,s.depth);
      ArrayList<State> act=new ArrayList<State>();
   //  System.out.println(v.depth+" "+d);
     
      if(v.depth==d)
      {
          
          return v;
      }
      player=opponent;
      v.cst=(int) Double.POSITIVE_INFINITY;
      act=getstates(player,s,cloneboard,cell,d);
       for(i=0;i<act.size();i++)
    {
     // System.out.println(v.cst);
       
    v.cst=Math.min(v.cst,max(player,act.get(i),act.get(i).board,cell,finalboard).cst);
    v.board=act.get(i).board;
   //printmatrix(v.board);
  
    } 
      // System.out.println("min"+v.cst);
//System.out.println(v.depth+" "+d);
//System.out.println("min");
      // System.out.println(v.cst);
   
   
      return v;
      }
      
      public ArrayList<State> getstates(String player,State s,String[][] cloneboard,String[][] cell,int d)
     {
         ArrayList<State> action=new ArrayList<State>();
         int i,j,p,k;
         
         String[][] newboard;
         State v;
         
         int cst;
          for(i=0;i<n;i++)
               for(j=0;j<n;j++)
                   
                   if(cloneboard[i][j].equals("."))
                 {
                     newboard=new String[cloneboard.length][cloneboard.length];
                for(p=0;p<n;p++)
               for(k=0;k<n;k++)
               { 
               newboard[p][k]=cloneboard[p][k];               
               }
                 
                     if(player.equals("X"))
                     {
                       newboard[i][j]="X";   
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);
                    
                     }
                     else if(player.equals("O"))
                     {
                         newboard[i][j]="O"; 
                      cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth));
                      v.depth=v.depth+1;
                      action.add(v);
                    
                      
                   
                     }
                         
                 }
     
          
     return action;
}          
      public ArrayList<State> getstates1(String player,State s,String[][] cloneboard,String[][] cell,int d)
     {
         ArrayList<State> action=new ArrayList<State>();
         int i,j,p,k;
         
         String[][] newboard;
         State v;
         
         int cst;
          for(i=0;i<n;i++)
               for(j=0;j<n;j++)
                   if(cloneboard[i][j].equals("."))
                 {
                     newboard=new String[cloneboard.length][cloneboard.length];
                for(p=0;p<n;p++)
               for(k=0;k<n;k++)
               { 
               newboard[p][k]=cloneboard[p][k];               
               }
                    
                     if(player.equals("X"))
                     {
                         if(cloneboard[i-1][j].equals("O"))   
                         {
                          newboard[i][j]="X";  
                          newboard[i-1][j]="X"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                         else if(cloneboard[i][j-1].equals("O"))
                             {
                          newboard[i][j]="X";
                          newboard[i][j-1]="X"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                         else if(cloneboard[i+1][j].equals("O"))
                             {
                          newboard[i][j]="X";   
                          newboard[i+1][j]="X"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                         else if(cloneboard[i][j+1].equals("O"))
                             {
                          newboard[i][j]="X";  
                          newboard[i][j+1]="X"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                         else
                         {
                       newboard[i][j]="X";   
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);
                         }
                     }
                     else if(player.equals("O"))
                     {
                          if(cloneboard[i-1][j].equals("X"))   
                         {
                          newboard[i][j]="O";   
                          newboard[i-1][j]="O"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                          else if(cloneboard[i][j-1].equals("X"))
                              {
                          newboard[i][j]="O"; 
                          newboard[i][j-1]="O"; 
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                          else if(cloneboard[i+1][j].equals("X"))
                              {
                          newboard[i+1][j]="O";   
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                          else if(cloneboard[i][j+1].equals("X"))
                              {
                          newboard[i][j+1]="O";   
                     cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth)); 
                      v.depth=v.depth+1;
                    action.add(v);    
                         }
                         else
                         {
                         newboard[i][j]="O"; 
                      cst=getscore(player,s,newboard,cell,i,j); 
                      v=(new State(cst,newboard,s.depth));
                      v.depth=v.depth+1;
                      action.add(v);
                         }
                      
                   
                     }
                         
                 }
     
          
     return action;
}          
      
       public int getscore(String player,State s,String[][] board,String[][] cell,int i,int j)
      {
          int result=0;
          int p,k;
          for(p=0;p<n;p++)
              for(k=0;k<n;k++)
           if(board[p][k].equals(play))
           {
              
               result+=Integer.parseInt(cell[p][k]);
           }
           else if(board[p][k].equals(opp))
           {
              
               result-=Integer.parseInt(cell[p][k]);
           }
     
          return result;
      
      }
      
      public void printmatrix(String[][] cloneboard)
      {
          int j,k;
      for(j=0;j<n;j++)
      {
        for(k=0;k<n;k++)
            System.out.print(cloneboard[j][k]+" ");
    System.out.println();
      }
    
      }
      

}

