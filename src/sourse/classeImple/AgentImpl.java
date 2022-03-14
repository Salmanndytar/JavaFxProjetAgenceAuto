package sourse.classeImple;


import sourse.classes.Agent;
import sourse.interphaces.IAgent;
import sourse.outils.DB;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AgentImpl implements IAgent {
    DB db =new DB();

    @Override
    public Agent getAgent(int id) {
        Agent agent = null;

        try {
            String sql = "SELECT * FROM agent WHERE id_agent= ?";
            db.preparedStatement(sql);
            db.getPstmt().setInt(1, id);
            ResultSet rs = db.executeQuery();
            if (rs.next())
            {
                agent = new Agent();
                agent.setId(rs.getInt(1));
                agent.setNom(rs.getString(2));
                agent.setPrenom(rs.getString(3));
                agent.setTel(rs.getString(4));
                agent.setEtat(rs.getInt(5));
            }
        }
        catch (Exception ex){ex.printStackTrace();}

        return agent;
    }
    @Override
    public int suppAgent(int id) {
        int ok=0;
        String sql = "DELETE FROM agent WHERE id_agent= ?";
        try {
            db.preparedStatement(sql);
            db.getPstmt().setInt(1,id);
            ok= db.executUpdate(sql);
        }catch (Exception ex){ex.printStackTrace();}


        return ok;
    }
    @Override
    public int addAgent(Agent agent) {
        int insert=0;
        try
        {
            String sql="INSERT INTO agent VALUES(NULL, ?, ?, ?, ?,NULL)";

            db.preparedStatement(sql);
            db.getPstmt().setString(1,agent.getNom());
            db.getPstmt().setString(2,agent.getPrenom());
            db.getPstmt().setString(3,agent.getTel());
            db.getPstmt().setInt(4,agent.getEtat());
            insert=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return insert;
    }
    @Override
    public int updatAgent(Agent agent) {
        int edite=0;
        try
        {
            String sql="UPDATE agent SET nom_agent = ?,prenom_agent = ?,tel= ?,etat = ? WHERE id_agent = ?";

            db.preparedStatement(sql);

            db.getPstmt().setString(1,agent.getNom());
            db.getPstmt().setString(2,agent.getPrenom());
            db.getPstmt().setString(3,agent.getTel());
            db.getPstmt().setInt(4, agent.getEtat());
            db.getPstmt().setInt(5,agent.getId());
            edite=db.executUpdate(sql);

        }catch (Exception ex){ex.printStackTrace();}
        return edite;
    }
    @Override
    public List<Agent> listeAgent() {
        String sql="SELECT * FROM agent ";
        List<Agent>agentList= new ArrayList<Agent>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Agent agent = new Agent();
                agent.setId(rs.getInt(1));
                agent.setNom(rs.getString(2));
                agent.setPrenom(rs.getString(3));
                agent.setTel(rs.getString(4));
                agent.setEtat(rs.getInt(5));
                agentList.add(agent);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return agentList;
    }
    @Override
    public List<Agent> listeAgentEtatNonActive() {
        String sql="SELECT * FROM agent  WHERE etat=0 OR etat=NULL";
        List<Agent>agentList= new ArrayList<Agent>();
        try
        {
            db.preparedStatement(sql);
            ResultSet rs=db.executeQuery();
            while (rs.next())
            {
                Agent agent = new Agent();
                agent.setId(rs.getInt(1));
                agent.setNom(rs.getString(2));
                agent.setPrenom(rs.getString(3));
                agent.setTel(rs.getString(4));
                agentList.add(agent);
            }

        }catch (Exception ex){ex.printStackTrace();}
        return agentList;
    }
}

