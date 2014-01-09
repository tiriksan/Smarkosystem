package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.sql.DataSource;
import spring.ov13.domene.Bruker;
import spring.ov13.domene.Emne;

public class DatabaseForTesting {

    private String dbNavn;
    private String dbUser;
    private String dbPswrd;
    private Connection forbindelse;
  
    private final String sqlSelectAlleBrukere = "Select * from bruker order by etternavn";
    private final String sqlInsertBruker = "insert into bruker values(?,?,?,?,?)";
    private final String sqlInsertBrukere = "insert into bruker values(?,?,?,?,?)";
    private final String sqlUpdateBruker = "update bruker set fornavn=?,etternavn=?, brukertype=?, passord=? where brukernavn=?";
    private final String sqlSelectAlleFag = "Select * from fag order by emnekode";
    private final String sqlInsertFag= "insert into fag values(?,?,?,?,?)";
    private final String sqlUpdateFag= "update fag set fagnavn=?,emnekode=?";
    
    
    public DatabaseForTesting(DataSource ds) {
        
    }

    
    public DatabaseForTesting() {
    }
    
    protected Connection getForbindelse() {
        return forbindelse;
    }

    private void åpneForbindelse() throws Exception {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
           // org.apache.derby.jdbc.
          //  Class.forName("org.apache.derby.jdbc.ClientDriver");
            forbindelse = DriverManager.getConnection(dbNavn, dbUser, dbPswrd);
            System.out.println("Databaseforbindelse opprettet");
        } catch (SQLException e) {
            Opprydder.skrivMelding(e, "KonstruktÃ¸ren");
            Opprydder.lukkForbindelse(forbindelse);
        }
    }

    private void lukkForbindelse() {
        System.out.println("Lukker databaseforbindelsen");
        Opprydder.lukkForbindelse(forbindelse);
    }
    
    public synchronized boolean registrerBruker(Bruker bruker){
        boolean ok = false;
        System.out.println("registrerBruker()");
        PreparedStatement psInsertBruker = null;
        bruker.setPassord(java.util.UUID.randomUUID().toString().substring(0,10));
        
        try {
            åpneForbindelse();
            psInsertBruker = forbindelse.prepareStatement(sqlInsertBruker);
            psInsertBruker.setString(1, bruker.getBrukernavn());
            psInsertBruker.setString(2, bruker.getFornavn());
            psInsertBruker.setString(3, bruker.getEtternavn());
            System.out.println(bruker.getPassord());
            psInsertBruker.setString(4, bruker.getPassord());
            psInsertBruker.setInt(5, bruker.getBrukertype());
            

            int i = psInsertBruker.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
           // Opprydder.lukkSetning(psInsertBruker);
        }
        lukkForbindelse();
        return ok;
        
    
    }
    public synchronized boolean registrerBrukere(ArrayList<Bruker> brukere){
        boolean ok = false;
        System.out.println("registrerBrukere()");
        PreparedStatement psInsertBrukere = null;

        try {
            åpneForbindelse();
               for (int i = 0; i < brukere.size(); i++) {
              
            psInsertBrukere = forbindelse.prepareStatement(sqlInsertBrukere);
            psInsertBrukere.setString(1, brukere.get(i).getBrukernavn());
            psInsertBrukere.setString(2, brukere.get(i).getFornavn());
            psInsertBrukere.setString(3, brukere.get(i).getEtternavn());
            psInsertBrukere.setString(4, brukere.get(i).getPassord());
            psInsertBrukere.setInt(5, brukere.get(i).getBrukertype());
            
            int j = psInsertBrukere.executeUpdate();
            if (j > 0) {
                ok = true;
            }
        }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
           // Opprydder.lukkSetning(psInsertBruker);
        }
        lukkForbindelse();
        return ok;
        
    
    }
    
    public ArrayList<Bruker> getAlleBrukere() {
        System.out.println("getAlleBrukere()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Bruker> brukerListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleBrukere);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Bruker b = new Bruker(res.getString("brukernavn"), res.getString("fornavn"), res.getString("etternavn"), res.getInt("hovedbrukertype"), res.getString("passord"));
                if (brukerListe == null) {
                    brukerListe = new ArrayList<Bruker>();
                }
                brukerListe.add(b);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleBrukere()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleBrukere - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            //Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return brukerListe;
    }

    public synchronized boolean oppdaterBruker(Bruker bruker){
        boolean ok = false;
        System.out.println("oppdaterBruker()");
        PreparedStatement psUpdateBruker = null;
        
        try{
            åpneForbindelse();
            psUpdateBruker = forbindelse.prepareStatement(sqlUpdateBruker);
            psUpdateBruker.setString(1, bruker.getBrukernavn());
            psUpdateBruker.setString(2,bruker.getFornavn());
            psUpdateBruker.setString(3, bruker.getEtternavn());
            psUpdateBruker.setInt(4, bruker.getBrukertype());
            psUpdateBruker.setString(5, bruker.getPassord());
            int i = psUpdateBruker.executeUpdate();
            if(i>0){
                ok = true;
            }
            
            } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterBruker()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterBruker - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateBruker);
        }
        lukkForbindelse();
        return ok;
    }
    
    
    //fag metoder //
    
    public synchronized boolean registrerFag(Emne fag){
        boolean ok = false;
        System.out.println("registrerFag()");
        PreparedStatement psInsertFag = null;

        try {
            åpneForbindelse();
            psInsertFag = forbindelse.prepareStatement(sqlInsertFag);
            psInsertFag.setString(1, fag.getEmnenavn());
            psInsertFag.setString(2, fag.getEmnekode());
            

            int i = psInsertFag.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
           // Opprydder.lukkSetning(psInsertFag);
        }
        lukkForbindelse();
        return ok;
    
    }
    
       public ArrayList<Emne> getAlleFag() {
        System.out.println("getAlleFag()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Emne> fagListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleFag);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Emne f = new Emne(res.getString("fagnavn"), res.getString("emnekode"));
                if (fagListe == null) {
                    fagListe = new ArrayList<Emne>();
                }
                fagListe.add(f);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return fagListe;
    }

    public synchronized boolean oppdaterFag(Emne bruker){
        boolean ok = false;
        System.out.println("oppdaterFag()");
        PreparedStatement psUpdateFag = null;
        
        try{
            åpneForbindelse();
            psUpdateFag = forbindelse.prepareStatement(sqlUpdateFag);
            psUpdateFag.setString(1, bruker.getEmnenavn());
            psUpdateFag.setString(2,bruker.getEmnekode());
            int i = psUpdateFag.executeUpdate();
            if(i>0){
                ok = true;
            }
            
            } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterFag()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterFag - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateFag);
        }
        lukkForbindelse();
        return ok;
    }
    
    
    
}
            
            
            
        
    

    

