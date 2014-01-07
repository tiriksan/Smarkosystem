package spring.ov13.domene.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import spring.ov13.domene.Vare;

public class Database {

    private String dbNavn;
    private Connection forbindelse;
    private final String sqlDeleteVare = "Delete from ov13.varer where varenr = ?";
    private final String sqlSelectAlleVarer = "Select * from ov13.varer order by varenr";
    private final String sqlInsertVare = "insert into ov13.varer values(?,?,?)";
    private final String sqlUpdateVare = "update ov13.varer set varenavn=?,pris=? where varenr=?";

    public Database(String dbNavn) {
        this.dbNavn = dbNavn;
    }

    
    public Database() {
    }

    private void åpneForbindelse() throws Exception {
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            forbindelse = DriverManager.getConnection(dbNavn);
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

    public ArrayList<Vare> getAlleVarer() {
        System.out.println("getAlleVarer()");
        PreparedStatement psSelectAlle = null;
        ResultSet res;
        ArrayList<Vare> vareListe = null;
        try {
            åpneForbindelse();
            psSelectAlle = forbindelse.prepareStatement(sqlSelectAlleVarer);
            res = psSelectAlle.executeQuery();
            while (res.next()) {
                Vare v = new Vare(res.getInt("varenr"), res.getString("varenavn"), res.getInt("pris"));
                if (vareListe == null) {
                    vareListe = new ArrayList<Vare>();
                }
                vareListe.add(v);
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "getAlleVarer()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "getAlleVarer - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psSelectAlle);
        }
        lukkForbindelse();
        return vareListe;
    }
    public synchronized boolean registrerVare(Vare v) {
        boolean ok = false;
        System.out.println("registrerVare()");
        PreparedStatement psInsertVare = null;

        try {
            åpneForbindelse();
            psInsertVare = forbindelse.prepareStatement(sqlInsertVare);
            psInsertVare.setInt(1, v.getVarenr());
            psInsertVare.setString(2, v.getVarenavn());
            psInsertVare.setInt(3, v.getPris());

            int i = psInsertVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "registrerVare()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "registrerVare - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psInsertVare);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean oppdaterVare(Vare v) {
        boolean ok = false;
        System.out.println("oppdaterVare()");
        PreparedStatement psUpdateVare = null;

        try {
            åpneForbindelse();
            psUpdateVare = forbindelse.prepareStatement(sqlUpdateVare);
            psUpdateVare.setInt(3, v.getVarenr());
            psUpdateVare.setString(1, v.getVarenavn());
            psUpdateVare.setInt(2, v.getPris());
            System.out.println("pris: " + v.getPris());
            int i = psUpdateVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "oppdaterPerson()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "oppdaterPerson - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psUpdateVare);
        }
        lukkForbindelse();
        return ok;
    }

    public synchronized boolean slettVare(Vare v) {
        boolean ok = false;
        System.out.println("slettPerson()");
        PreparedStatement psDeleteVare = null;

        try {
            åpneForbindelse();
            psDeleteVare = forbindelse.prepareStatement(sqlDeleteVare);
            psDeleteVare.setInt(1, v.getVarenr());

            int i = psDeleteVare.executeUpdate();
            if (i > 0) {
                ok = true;
            }
        } catch (SQLException e) {
            Opprydder.rullTilbake(forbindelse);
            Opprydder.skrivMelding(e, "slettVare()");
        } catch (Exception e) {
            Opprydder.skrivMelding(e, "slettVare - ikke sqlfeil");
        } finally {
            Opprydder.settAutoCommit(forbindelse);
            Opprydder.lukkSetning(psDeleteVare);
        }
        lukkForbindelse();
        return ok;

    }

}
