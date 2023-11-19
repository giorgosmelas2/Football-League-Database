import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;


public class FootballLeagueGUI extends javax.swing.JFrame {

    private JPanel northPanel, westPanel, centerPanel, eastPanel, southPanel;
    private JLabel description;
    private RoundedButton teamsBtn, playersBtn, scoreboardTableBtn, gameDayBtn, throphiesBtn, addBtn, deleteBtn, updateBtn, button1, button2, button3;
    private JScrollPane scrollPane;
    private JTable table;
    private JTextArea message;
    private GridBagConstraints c;
    private Component[] components;
    private DatabaseTable dbTable;
    private final String[] teamsColumnNames = {"Id", "Name", "Field", "Number Of Players"};
    private final String[] playersColumnNames = {"Id", "Name", "Age","Club", "Goals", "Position"};
    private final String[] scoreboardColumnNames = {"Id", "Team", "Wins", "Draws", "Losses", "Points"};
    private final String[] footbllMatchesColumnNames = {"Id", "Home Team", "Away Team", "Date", "Time", "Score"};
    
    public FootballLeagueGUI() {
        initComponentsNew();
    }
    
    private void initComponentsNew(){
        
        //Making the size of application's window same for every pc
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = (int) toolkit.getScreenSize().getWidth();
        int screenHeight = (int) toolkit.getScreenSize().getHeight();
        
        double widthFraction = 0.6;
        double heightFraction = 0.6;
        
        int windowWidth = (int)(screenWidth * widthFraction);
        int windowHeight = (int)(screenHeight * heightFraction);
        
        setSize(windowWidth,windowHeight);
        setTitle("SuperLeague Database");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        
        //Making 5 panels in BorderLayout
        setLayout(new BorderLayout());
        
        northPanel = new JPanel();
        westPanel = new JPanel();
        centerPanel = new JPanel();
        eastPanel = new JPanel();
        southPanel = new JPanel();
       
        
        //Setting dimensions of every panel
        northPanel.setPreferredSize(new Dimension(windowWidth, (int)(windowHeight * 0.1)));
        westPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.2), (int)(windowHeight * 0.8)));
        centerPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.6), (int)(windowHeight * 0.8)));
        eastPanel.setPreferredSize(new Dimension((int)(windowWidth * 0.2), (int)(windowHeight * 0.8)));
        southPanel.setPreferredSize(new Dimension(windowWidth, (int)(windowHeight * 0.1)));
        
        add(northPanel,BorderLayout.NORTH);
        add(westPanel,BorderLayout.WEST);
        add(centerPanel,BorderLayout.CENTER);
        add(eastPanel,BorderLayout.EAST);
        add(southPanel,BorderLayout.SOUTH);
        
        northPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 0;
        description = new JLabel("This is an application which gives you access in Premiere League's Database");
        northPanel.add(description, c);
        
        
        
        //In west panel user can select every array of our database
        westPanel.setLayout(new GridBagLayout());
        
        c.insets = new Insets(15,17,15,17);
        
        teamsBtn = new RoundedButton("Teams"); 
        c.gridy = 0;
        westPanel.add(teamsBtn,c); 
        
        playersBtn = new RoundedButton("Players");   
        c.gridy = 1;
        westPanel.add(playersBtn,c);
        
        scoreboardTableBtn = new RoundedButton("Scoreboard");
        c.gridy = 2;
        westPanel.add(scoreboardTableBtn,c);
        
        gameDayBtn = new RoundedButton("Game Days");
        c.gridy = 3;
        westPanel.add(gameDayBtn,c);
        
        throphiesBtn = new RoundedButton("Throphies");
        c.gridy = 4;
        westPanel.add(throphiesBtn,c);
        
        teamsBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeTextArea();
                createTable(teamsColumnNames);
                teamsQueries(eastPanel);
                databaseConnect("TEAMS");               
            }
        });
        
        playersBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeTextArea();
                createTable(playersColumnNames);
                playersQueries();
                databaseConnect("PLAYERS"); 
            }
        });
        
        scoreboardTableBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeTextArea();
                createTable(scoreboardColumnNames);
                leagueTableQueries();
                databaseConnect("SCOREBOARD");
            }
        });
        
        gameDayBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                removeTextArea();
                createTable(footbllMatchesColumnNames);
                gameDayQueries();
                databaseConnect("FOOTBALLMATCHES");
            }
        });
        
        throphiesBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                punishmentsQueries();
            }
        });
        
        //In center panel will be displayed info for selected array
        centerPanel.setLayout(new BorderLayout());
        message = new JTextArea("Select a table to see its info.");
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        scrollPane = new JScrollPane(message);
        centerPanel.add(scrollPane);
        
        
        
        //East panel has some queries differnent for each table
        eastPanel.setLayout(new GridBagLayout());
        
        
        
        //South panel has the three classic SQL queries add, delete, update
        
        addBtn = new RoundedButton("Add");
        deleteBtn = new RoundedButton("Delete");
        updateBtn = new RoundedButton("Update");
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 3;
        
        southPanel.add(addBtn,c);               
        southPanel.add(deleteBtn,c);
        southPanel.add(updateBtn,c);
 
    }   
    

    private void teamsQueries(JPanel eastPanel){
        components = eastPanel.getComponents();
        for (Component component : components) {
            if (component instanceof RoundedButton) {
                eastPanel.remove(component);
            }
        }
        eastPanel.revalidate();
        eastPanel.repaint();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        button1 = new RoundedButton("teams 1");
        c.gridy = 0;
        eastPanel.add(button1,c); 
        
        button2 = new RoundedButton("teams 2");
        c.gridy = 1;
        eastPanel.add(button2,c);
        
        button3 = new RoundedButton("teams 3");
        c.gridy = 2;  
        eastPanel.add(button3,c);
    }
    
    private void playersQueries(){
        components = eastPanel.getComponents();
        for (Component component : components) {
            if (component instanceof RoundedButton) {
                eastPanel.remove(component);
            }
        }
        eastPanel.revalidate();
        eastPanel.repaint();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        button1 = new RoundedButton("players 1");
        c.gridy = 0;
        eastPanel.add(button1,c);
        button2 = new RoundedButton("players 2");
        c.gridy = 1;
        eastPanel.add(button2,c);
        button3 = new RoundedButton("players 3");
        c.gridy = 2;
        eastPanel.add(button3,c);
        
    }
    
    private void leagueTableQueries(){
        components = eastPanel.getComponents();
        for (Component component : components) {
            if (component instanceof RoundedButton) {
                eastPanel.remove(component);
            }
        }
        eastPanel.revalidate();
        eastPanel.repaint();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        button1 = new RoundedButton("league tables 1");
        c.gridy = 0;
        eastPanel.add(button1,c);
        
        button2 = new RoundedButton("league tables 2");
        c.gridy = 1;
        eastPanel.add(button2,c);
        
        button3 = new RoundedButton("league tables 3");
        c.gridy = 3;
        eastPanel.add(button3,c);
        
        
    }
    
    private void gameDayQueries(){
        components = eastPanel.getComponents();
        for (Component component : components) {
            if (component instanceof RoundedButton) {
                eastPanel.remove(component);
            }
        }
        eastPanel.revalidate();
        eastPanel.repaint();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        button1 = new RoundedButton("game day 1");
        c.gridy = 0;
        eastPanel.add(button1,c);
        
        button2 = new RoundedButton("game day 2");
        c.gridy = 1;
        eastPanel.add(button2,c);
        
        button3 = new RoundedButton("game day 3");
        c.gridy = 2;
        eastPanel.add(button3,c);
    }
    
    private void punishmentsQueries(){
        components = eastPanel.getComponents();
        for (Component component : components) {
            if (component instanceof RoundedButton) {
                eastPanel.remove(component);
            }
        }
        eastPanel.revalidate();
        eastPanel.repaint();
        
        c.fill = GridBagConstraints.VERTICAL;
        c.gridwidth = 3;
        
        button1 = new RoundedButton("throphies 1");
        c.gridy = 0;
        eastPanel.add(button1,c);
        button2 = new RoundedButton("throphies 2");
        c.gridy = 1;
        eastPanel.add(button2,c);
        button3 = new RoundedButton("throphies 3");
        c.gridy = 2;
        eastPanel.add(button3,c);
    }
    
    private void removeTextArea(){
        components = centerPanel.getComponents();
        centerPanel.remove(components[components.length - 1]);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
    
    private void createTable(String [] tableColumnNames){
        dbTable = new DatabaseTable(tableColumnNames.length, tableColumnNames);
        table = new JTable(dbTable);
        scrollPane.setViewportView(table);
        centerPanel.add(scrollPane, BorderLayout.CENTER);                 
    }
    
    private void databaseConnect(String tableName){
        DatabaseDriver db = new DatabaseDriver();
        try{
                db.showTable(tableName, dbTable);
            }catch(ClassNotFoundException ex){
                ex.printStackTrace();
            }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FootballLeagueGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
