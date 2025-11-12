import java.util.*;
import javax.swing.*;
import java.awt.*;

public class MaritimeReliefRouteOptimization extends JFrame {

    // Distance Matrix (Adjacency Matrix)
    static int[][] distanceMatrix = {
        {0, 15, 25, 35},
        {15, 0, 30, 28},
        {25, 30, 0, 20},
        {35, 28, 20, 0}
    };

    // Location names
    static String[] locations = {"Port A", "Port B", "Relief Center C", "Relief Center D"};

    // GUI Components
    private JTextArea outputArea;

    public MaritimeReliefRouteOptimization() {
        // Set up the frame
        setTitle("Maritime Relief Route Optimization");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(230, 230, 230));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(230, 230, 230));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 30, 0));
        JLabel titleLabel = new JLabel("MARITIME RELIEF ROUTE OPTIMIZATION");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(40, 40, 40));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(new Color(230, 230, 230));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        // Button Panel (Left Side)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(9, 1, 0, 8));
        buttonPanel.setPreferredSize(new Dimension(280, 0));
        buttonPanel.setBackground(new Color(230, 230, 230));

        // Create Buttons with exact colors from image
        JButton btnGreedy = createButton("Greedy Algorithm", new Color(92, 184, 92));
        JButton btnDP = createButton("Dynamic Programming", new Color(91, 192, 222));
        JButton btnBacktrack = createButton("Backtracking", new Color(240, 173, 78));
        JButton btnDivideConquer = createButton("Divide & Conquer", new Color(171, 137, 204));
        JButton sortSearchBtn = createButton("Sorting & Searching", new Color(132, 146, 166));
        JButton btnRunAllTSP = createButton("RUN ALL TSP", new Color(217, 83, 79));

       
        // Add Action Listeners
        btnGreedy.addActionListener(e -> runGreedy());
        btnDP.addActionListener(e -> runDP());
        btnBacktrack.addActionListener(e -> runBacktrack());
        btnDivideConquer.addActionListener(e -> runDivideConquer());
        sortSearchBtn.addActionListener(e -> runSortingAndSearching());
        btnRunAllTSP.addActionListener(e -> runAllTSP());    

        

        // Add buttons to panel
        buttonPanel.add(btnGreedy);
        buttonPanel.add(btnDP);
        buttonPanel.add(btnBacktrack);
        buttonPanel.add(btnDivideConquer);
        buttonPanel.add(sortSearchBtn);
        buttonPanel.add(btnRunAllTSP);
        
        

        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Output Area (Right Side)
        outputArea = new JTextArea();
        outputArea.setFont(new Font("Courier New", Font.PLAIN, 13));
        outputArea.setEditable(false);
        outputArea.setBackground(Color.WHITE);
        outputArea.setForeground(new Color(60, 60, 60));
        outputArea.setText("Click a button to run algorithm");
        outputArea.setMargin(new Insets(15, 15, 15, 15));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 1));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 15));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(280, 50));
        
        return button;
    }

    // Algorithm Methods
    
    private void runGreedy() {
        outputArea.setText(greedyTSP(distanceMatrix));
    }

    
    private void runDP() {
        outputArea.setText(dynamicProgrammingTSP(distanceMatrix));
    }

    private void runBacktrack() {
        outputArea.setText(backtrackingTSP(distanceMatrix));
    }

    private void runDivideConquer() {
        outputArea.setText(divideAndConquerTSP(distanceMatrix));
    }
    
   public void runSortingAndSearching() {
        int[] sortArr = {8, 3, 5, 1, 9, 2};
        int[] searchArr = {1, 2, 3, 5, 8, 9};
        int target = 5;
    
        outputArea.setText(sortingAndSearchingTSP(sortArr, searchArr, target));
    }

    private void runAllTSP() {
        StringBuilder sb = new StringBuilder();
        sb.append(greedyTSP(distanceMatrix)).append("\n\n");
        sb.append(dynamicProgrammingTSP(distanceMatrix)).append("\n\n");
        sb.append(backtrackingTSP(distanceMatrix)).append("\n\n");
        sb.append(divideAndConquerTSP(distanceMatrix)).append("\n\n");
    
        // Sorting & Searching Output Added to Display
        int[] sortArr = {8, 3, 5, 1, 9, 2};
        int[] searchArr = {1, 2, 3, 5, 8, 9};
        int target = 5;
        sb.append(sortingAndSearchingTSP(sortArr, searchArr, target)).append("\n\n");
    
        outputArea.setText(sb.toString());
        }
        
        //--------------------------- Greedy --------------------------
    /**
     * @author Zakirah
     * Solves TSP using Greedy Programming with memoization
     */
    public static String greedyTSP(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        int current = 0;
        visited[current] = true;
        int totalDistance = 0;
        StringBuilder route = new StringBuilder(locations[current]);

        for (int i = 0; i < n - 1; i++) {
            int minDist = Integer.MAX_VALUE;
            int nextCity = -1;

            for (int j = 0; j < n; j++) {
                if (!visited[j] && dist[current][j] < minDist && dist[current][j] > 0) {
                    minDist = dist[current][j];
                    nextCity = j;
                }
            }

            if (nextCity != -1) {
                visited[nextCity] = true;
                totalDistance += minDist;
                route.append(" -> ").append(locations[nextCity]);
                current = nextCity;
            }
        }

        totalDistance += dist[current][0];
        route.append(" -> ").append(locations[0]);

        return "---- Greedy TSP ---- \n\n " + route + " | Total Distance: " + totalDistance + " nm";
    }

    //--------------------------- Dynamic TSP --------------------------
    /**
     * @author Aqilah
     * Solves TSP using Dynamic Programming with memoization
     */public static String dynamicProgrammingTSP(int[][] dist) {
        int n = dist.length;
        int VISITED_ALL = (1 << n) - 1;
        int[][] memo = new int[n][1 << n];
        
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }

        int totalDistance = dynamicProgrammingTSPHelper(0, 1, dist, memo, VISITED_ALL);

        // Reconstruct path
        StringBuilder route = new StringBuilder(locations[0]);
        int mask = 1;
        int pos = 0;

        while (mask != VISITED_ALL) {
            int nextCity = -1;
            int minCost = Integer.MAX_VALUE;

            for (int city = 0; city < n; city++) {
                if ((mask & (1 << city)) == 0) {
                    int newMask = mask | (1 << city);
                    int cost = dist[pos][city] + (memo[city][newMask] != -1 ? memo[city][newMask] : 0);
                    if (cost < minCost) {
                        minCost = cost;
                        nextCity = city;
                    }
                }
            }

            if (nextCity != -1) {
                route.append(" -> ").append(locations[nextCity]);
                mask |= (1 << nextCity);
                pos = nextCity;
            } else break;
        }

        route.append(" -> ").append(locations[0]);

        return "---- Dynamic Programming TSP ----\n\n " + route + " | Total Distance: " + totalDistance + " nm";
    }

    private static int dynamicProgrammingTSPHelper(int pos, int mask, int[][] dist, int[][] memo, int VISITED_ALL) {
        if (mask == VISITED_ALL) {
            return dist[pos][0];
        }

        if (memo[pos][mask] != -1) {
            return memo[pos][mask];
        }

        int minCost = Integer.MAX_VALUE;

        for (int city = 0; city < dist.length; city++) {
            if ((mask & (1 << city)) == 0) {
                int newCost = dist[pos][city] + dynamicProgrammingTSPHelper(city, mask | (1 << city), dist, memo, VISITED_ALL);
                minCost = Math.min(minCost, newCost);
            }
        }

        memo[pos][mask] = minCost;
        return minCost;
    }

    //--------------------------- Backtracking TSP --------------------------
    /**
     * @author Safiyah
     * Solves TSP using Backtracking Programming with memoization
     */
    public static String backtrackingTSP(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        visited[0] = true;
        int[] minCost = {Integer.MAX_VALUE};
        StringBuilder bestPath = new StringBuilder();
        StringBuilder currentPath = new StringBuilder(locations[0]);

        tspBacktracking(0, dist, visited, n, 1, 0, currentPath, minCost, bestPath);

        return "---- Backtracking TSP ----\n\n " + bestPath + " | Total Distance: " + minCost[0] + " nm";
    }

    private static void tspBacktracking(int pos, int[][] dist, boolean[] visited, int n, int count, int cost, StringBuilder path, int[] minCost, StringBuilder bestPath) {
        if (count == n && dist[pos][0] > 0) {
            int totalCost = cost + dist[pos][0];
            if (totalCost < minCost[0]) {
                minCost[0] = totalCost;
                bestPath.setLength(0);
                bestPath.append(path).append(" -> ").append(locations[0]);
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i] && dist[pos][i] > 0) {
                visited[i] = true;
                int pathLen = path.length();
                path.append(" -> ").append(locations[i]);
                
                tspBacktracking(i, dist, visited, n, count + 1, cost + dist[pos][i], path, minCost, bestPath);
                
                path.setLength(pathLen);
                visited[i] = false;
            }
        }
    }

    
    //--------------------------- Divide and Conquer TSP --------------------------
    /**
     * @author Firdauze
     * Solves TSP using Divide and Conquer TSP Programming with memoization
     */
    public static String divideAndConquerTSP(int[][] dist) {
        int n = dist.length;
        boolean[] visited = new boolean[n];
        visited[0] = true;
        int[] minCost = {Integer.MAX_VALUE};
        StringBuilder bestPath = new StringBuilder();
        StringBuilder currentPath = new StringBuilder(locations[0]);

        divideAndConquerHelper(0, visited, 0, dist, n, currentPath, minCost, bestPath);

        return "---- Divide & Conquer TSP ---- \n\n " + bestPath + " | Total Distance: " + minCost[0] + " nm";
    }

    private static void divideAndConquerHelper(int pos, boolean[] visited, int currentCost, int[][] dist, int n, StringBuilder path, int[] minCost, StringBuilder bestPath) {
        if (allVisited(visited)) {
            int totalCost = currentCost + dist[pos][0];
            if (totalCost < minCost[0]) {
                minCost[0] = totalCost;
                bestPath.setLength(0);
                bestPath.append(path).append(" -> ").append(locations[0]);
            }
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                int pathLen = path.length();
                path.append(" -> ").append(locations[i]);
                
                divideAndConquerHelper(i, visited, currentCost + dist[pos][i], dist, n, path, minCost, bestPath);
                
                path.setLength(pathLen);
                visited[i] = false;
            }
        }
    }

    private static boolean allVisited(boolean[] visited) {
        for (boolean v : visited) {
            if (!v) return false;
        }
        return true;
    }
    
    
        // -------------------- Sorting & Searching Section --------------------

    /**
     * @author Firdauze
     * Solves TSP using Divide and Conquer TSP Programming with memoization
     */
    public static String sortingAndSearchingTSP(int[] sortArr, int[] searchArr, int target) {
        insertionSort(sortArr);
        int index = binarySearch(searchArr, target);

        StringBuilder sb = new StringBuilder();
        sb.append("---- Sorting & Searching ----\n\n");
        sb.append("Sorted Array (Insertion Sort): ").append(Arrays.toString(sortArr)).append("\n\n");
        sb.append("Binary Search Array: ").append(Arrays.toString(searchArr)).append("\n");
        sb.append("Target: ").append(target).append("\n");
        sb.append("Found at Index → ").append(index);

        return sb.toString();
    }

    // Insertion Sort
    public static void insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;

            while (j >= 0 && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Binary Search
    public static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) return mid;
            else if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    //-------------------- Main method ------------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MaritimeReliefRouteOptimization frame = new MaritimeReliefRouteOptimization();
            frame.setVisible(true);
        });
    }
}