package sourse.interphaces;

import sourse.classes.Agent;

import java.util.List;

public interface IAgent {
    public Agent getAgent(int id);
    public int suppAgent(int id);
    public int addAgent(Agent agent);
    public  int updatAgent(Agent agent);
   // public  int updatAgentEtat(Agent agent);
    public List<Agent> listeAgent();
    public List<Agent>listeAgentEtatNonActive();
}
