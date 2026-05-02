import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DatabazaManager {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/zaloha_sql";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Connection connection;
    private boolean jePripojeny = false;
    
    public boolean pripoj() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            Connection tempConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", DB_USER, DB_PASSWORD);
            try (Statement stmt = tempConn.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS zaloha_sql");
            }
            tempConn.close();
            
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            jePripojeny = true;
            
            vytvorTabulky();
            
            System.out.println("Program pripojen k SQL databazi.");
            return true;
        } 
        catch (ClassNotFoundException e) {
            System.out.println("JDBC ovladac nebyl nalezen.");
            return false;
        } 
        catch (SQLException e) {
            System.out.println("Nepodarilo se pripojit: " + e.getMessage());
            return false;
        }
    }
    
    private void vytvorTabulky() {
        String sqlZamestnanci = """
            CREATE TABLE IF NOT EXISTS zamestnanci (
                id INT PRIMARY KEY,
                jmeno VARCHAR(100) NOT NULL,
                prijmeni VARCHAR(100) NOT NULL,
                rok_narozeni INT NOT NULL,
                typ VARCHAR(50) NOT NULL
            )
            """;
            
        String sqlSpoluprace = """
            CREATE TABLE IF NOT EXISTS spoluprace (
                id_zamestnance INT NOT NULL,
                id_kolegy INT NOT NULL,
                kvalita VARCHAR(20) NOT NULL,
                PRIMARY KEY (id_zamestnance, id_kolegy)
            )
            """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sqlZamestnanci);
            stmt.execute(sqlSpoluprace);
            System.out.println("Tabulky byly vytvoreny.");
        } 
        catch (SQLException e) {
            System.out.println("Chyba pri vytvareni tabulek: " + e.getMessage());
        }
    }
    
    public void odpoj() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                jePripojeny = false;
                System.out.println("SQL databaze odpojena.");
            }
        } 
        catch (SQLException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
    
    public ArrayList<Map<String, Object>> stiahniZamestnancov() {
        ArrayList<Map<String, Object>> zoznam = new ArrayList<>();
        if (!jePripojeny) return zoznam;
        
        String sql = "SELECT id, jmeno, prijmeni, rok_narozeni, typ FROM zamestnanci";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> z = new HashMap<>();
                z.put("id", rs.getInt("id"));
                z.put("jmeno", rs.getString("jmeno"));
                z.put("prijmeni", rs.getString("prijmeni"));
                z.put("rokNarozeni", rs.getInt("rok_narozeni"));
                z.put("typ", rs.getString("typ"));
                zoznam.add(z);
            }
            System.out.println("Pocet stazenych zamestnancu: " + zoznam.size() + ".");
            
        } 
        catch (SQLException e) {
            System.out.println(" Chyba: " + e.getMessage());
        }
        return zoznam;
    }
    
    public ArrayList<Map<String, Object>> stiahniSpoluprace() {
        ArrayList<Map<String, Object>> zoznam = new ArrayList<>();
        if (!jePripojeny) return zoznam;
        
        String sql = "SELECT id_zamestnance, id_kolegy, kvalita FROM spoluprace";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Map<String, Object> s = new HashMap<>();
                s.put("idZamestnance", rs.getInt("id_zamestnance"));
                s.put("idKolegy", rs.getInt("id_kolegy"));
                s.put("kvalita", rs.getString("kvalita"));
                zoznam.add(s);
            }
            System.out.println("Pocet stazenych spolupraci: " + zoznam.size() + ".");
            
        } 
        catch (SQLException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
        return zoznam;
    }
    
    public void ulozZamestnancov(EvidenceZamestnancu evidence) {
        if (!jePripojeny) return;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
            stmt.execute("DELETE FROM spoluprace");
            stmt.execute("DELETE FROM zamestnanci");
            stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
        } 
        catch (SQLException e) {
            System.out.println("Chyba pri mazani: " + e.getMessage());
            return;
        }
        
        
        String sql1 = "INSERT INTO zamestnanci (id, jmeno, prijmeni, rok_narozeni, typ) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql1)) {
            for (Zamestnanec z : evidence.getZamestnanci()) {
                pstmt.setInt(1, z.getID());
                pstmt.setString(2, z.getJmeno());
                pstmt.setString(3, z.getPrijmeni());
                pstmt.setInt(4, z.getRokNarozeni());
                pstmt.setString(5, z instanceof DatovyAnalytik ? "Datovy analytik" : "Bezpecnostni specialista");
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } 
        catch (SQLException e) {
            System.out.println("Chyba: " + e.getMessage());
            return;
        }
        
        
        String sql2 = "INSERT INTO spoluprace (id_zamestnance, id_kolegy, kvalita) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql2)) {
            for (Zamestnanec z : evidence.getZamestnanci()) {
                for (Spoluprace s : z.getSpoluprace()) {
                    pstmt.setInt(1, z.getID());
                    pstmt.setInt(2, s.getIdKolegy());
                    pstmt.setString(3, s.getKvalita().name());
                    pstmt.addBatch();
                }
            }
            pstmt.executeBatch();
            System.out.println("Data byla uspesne ulozena do SQL databazi.");
        } 
        catch (SQLException e) {
            System.out.println("Chyba: " + e.getMessage());
        }
    }
    
    public boolean jePripojeny() {
        return jePripojeny;
    }
}