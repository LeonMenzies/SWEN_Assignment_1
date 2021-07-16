/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 64 "model.ump"
// line 132 "model.ump"
public class Estate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Estate Associations
  private List<Player> playersInEstate;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Estate()
  {
    playersInEstate = new ArrayList<Player>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Player getPlayersInEstate(int index)
  {
    Player aPlayersInEstate = playersInEstate.get(index);
    return aPlayersInEstate;
  }

  public List<Player> getPlayersInEstate()
  {
    List<Player> newPlayersInEstate = Collections.unmodifiableList(playersInEstate);
    return newPlayersInEstate;
  }

  public int numberOfPlayersInEstate()
  {
    int number = playersInEstate.size();
    return number;
  }

  public boolean hasPlayersInEstate()
  {
    boolean has = playersInEstate.size() > 0;
    return has;
  }

  public int indexOfPlayersInEstate(Player aPlayersInEstate)
  {
    int index = playersInEstate.indexOf(aPlayersInEstate);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayersInEstate()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addPlayersInEstate(Player aPlayersInEstate)
  {
    boolean wasAdded = false;
    if (playersInEstate.contains(aPlayersInEstate)) { return false; }
    playersInEstate.add(aPlayersInEstate);
    if (aPlayersInEstate.indexOfEstate(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aPlayersInEstate.addEstate(this);
      if (!wasAdded)
      {
        playersInEstate.remove(aPlayersInEstate);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removePlayersInEstate(Player aPlayersInEstate)
  {
    boolean wasRemoved = false;
    if (!playersInEstate.contains(aPlayersInEstate))
    {
      return wasRemoved;
    }

    int oldIndex = playersInEstate.indexOf(aPlayersInEstate);
    playersInEstate.remove(oldIndex);
    if (aPlayersInEstate.indexOfEstate(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aPlayersInEstate.removeEstate(this);
      if (!wasRemoved)
      {
        playersInEstate.add(oldIndex,aPlayersInEstate);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayersInEstateAt(Player aPlayersInEstate, int index)
  {  
    boolean wasAdded = false;
    if(addPlayersInEstate(aPlayersInEstate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayersInEstate()) { index = numberOfPlayersInEstate() - 1; }
      playersInEstate.remove(aPlayersInEstate);
      playersInEstate.add(index, aPlayersInEstate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayersInEstateAt(Player aPlayersInEstate, int index)
  {
    boolean wasAdded = false;
    if(playersInEstate.contains(aPlayersInEstate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayersInEstate()) { index = numberOfPlayersInEstate() - 1; }
      playersInEstate.remove(aPlayersInEstate);
      playersInEstate.add(index, aPlayersInEstate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayersInEstateAt(aPlayersInEstate, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ArrayList<Player> copyOfPlayersInEstate = new ArrayList<Player>(playersInEstate);
    playersInEstate.clear();
    for(Player aPlayersInEstate : copyOfPlayersInEstate)
    {
      aPlayersInEstate.removeEstate(this);
    }
  }

}