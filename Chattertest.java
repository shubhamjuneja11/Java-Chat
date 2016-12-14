import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class Chattertest implements ActionListener
{
  Socket s;
    JFrame f,f2;
    JButton name,send,logout;
    JTextArea t,t1,t2,t3;
    JLabel text,t0;
    String n,s1;              //client name;
    ObjectInputStream din;
    ObjectOutputStream dout;
    boolean flag=true;//logout check
    public Chattertest()
    {try{
       s=new Socket("localhost",5366);
          //   System.out.println("client0");
             dout=new ObjectOutputStream(s.getOutputStream());
              //System.out.println("client1");
             din=new ObjectInputStream(s.getInputStream());
           
        }
        catch(Exception e){}
      f=new JFrame();
        name=new JButton("Login");
        t=new JTextArea();
        text=new JLabel();
        f.setSize(300,300);
        name.setBounds(80,200,100,50);
        t.setBounds(80,100,200,30);
       // t.setText("Hellooooooooo");
        text.setBounds(80,80,200,20);
        text.setText("Enter your name:");
        f.add(name);
        f.add(t);
        f.add(text);
        f.setLayout(null) ;
        f.setVisible(true);
        name.addActionListener(this);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         /*---------After Login-----------*/

        f2=new JFrame();
        t1=new JTextArea();  t2=new JTextArea();  t3=new JTextArea();
        t0=new JLabel();
        send=new JButton("send");
        logout=new JButton("LogOut");
        t0.setText("Enter message:");
        f2.setSize(900,900);
        t1.setBounds(10,10,400,300);  //all  messages
        t0.setBounds(10,400,200,20);  
        t2.setBounds(10,430,400,60);  //message to send
        send.setBounds(10,500,80,40);
        logout.setBounds(150,500,120,40);
        t3.setBounds(500,10,300,500);  //login logout
        
        t1.setEditable(false);
        t3.setEditable(false);
        f2.add(t1);  f2.add(t2);  f2.add(t3);f2.add(t0);
        f2.add(send);f2.add(logout);
        f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f2.setLayout(null);
        send.addActionListener(this);
        logout.addActionListener(this);
        
    }
    public void actionPerformed(ActionEvent e) 
    {
      if(e.getSource()==name)
      {
        try{ System.out.println("heelo0");
          n=t.getText().toString();
          System.out.println(n);
        f.setVisible(false);System.out.println("heelo2");
        f2.setVisible(true);System.out.println("heelo3");
        
        //t1.setText("hwllllllllllllll");
        clientchat();
         //f.removeAll();                                     //establish connection and Chattertest begins
        
        //Thread.sleep(20000);

           
            }
            catch(Exception e1){e1.printStackTrace();}
      }
      else if(e.getSource()==send)                    //send message
        try{
        s1=t2.getText().toString();System.out.println(s1);
        message o=new message(s1,n);System.out.println("sdref");
         dout.writeObject(o);System.out.println("uppypypy");
          dout.flush();
        t2.setText("");System.out.println("sdreeeeeeeed2");
      }
      catch(Exception e1){e1.printStackTrace();}
      else if(e.getSource()==logout)
        {flag=false;
                
        }

    }
    public void clientchat() throws IOException
    {System.out.println("c1");
      
      My m=new My(din,flag,this);
      Thread t=new Thread(m);
      t.start();
      System.out.println("c2");
      //BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
      /*do
      {
          s1=t2.getText();
          dout.writeObject(new message(s1,n,this));
          dout.flush();
      }while(flag);*/
      System.out.println("c3");
    }
    public void textprint(String a,String b)
    {
      t1.append(a+":"+b+"\n");System.out.println("Printtttttt");
    }
    public static void main(String s[])
    {
      new Chattertest();}
}
    class My implements Runnable
    {ObjectInputStream din;
     boolean flag;
      message ob;
      String s1,name;
      Chattertest o;
      public My(ObjectInputStream din,boolean flag,Chattertest o)
      {
           this.din=din;
           //System.out.println(din);
           this.flag=flag;
           this.o=o;
      }
      public void run()
      {
           try{System.out.println("c4");
            do
            {//System.out.println(din);
              ob=(message)din.readObject();System.out.println("c12");
              s1=ob.s;System.out.println("c13");
              //System.out.println("c14");
              name=ob.name;System.out.println("c15");
              if(flag)
              {
                     o.textprint(name,s1);
              }
              else break;
            }while(flag);
           }
           catch(Exception e2){e2.printStackTrace();}

      }
    } 
    class message implements Serializable
    {private static final long serialVersionUID = 5950169519310163575L;
       String s;
      // boolean status;
       String name;
      
      public message(String s,String name)
       {
        this.s=s;
        this.name=name;
       // this.status=status;
       }
    }
