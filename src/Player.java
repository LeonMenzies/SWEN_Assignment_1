/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.0.5692.1a9e80997 modeling language!*/


import java.util.*;

// line 42 "model.ump"
// line 98 "model.ump"
public class Player extends Move
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private boolean turn;

  //Player Associations
  private List<Card> hand;
  private List<Estate> estates;
  private List<Die> dies;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(boolean aTurn)
  {
    super();
    turn = aTurn;
    hand = new ArrayList<Card>();
    estates = new ArrayList<Estate>();
    dies = new ArrayList<Die>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTurn(boolean aTurn)
  {
    boolean wasSet = false;
    turn = aTurn;
    wasSet = true;
    return wasSet;
  }

  public boolean getTurn()
  {
    return turn;
  }
  /* Code from template association_GetMany */
  public Card getHand(int index)
  {
    Card aHand = hand.get(index);
    return aHand;
  }

  public List<Card> getHand()
  {
    List<Card> newHand = Collections.unmodifiableList(hand);
    return newHand;
  }

  public int numberOfHand()
  {
    int number = hand.size();
    return number;
  }

  public boolean hasHand()
  {
    boolean has = hand.size() > 0;
    return has;
  }

  public int indexOfHand(Card aHand)
  {
    int index = hand.indexOf(aHand);
    return index;
  }
  /* Code from template association_GetMany */
  public Estate getEstate(int index)
  {
    Estate aEstate = estates.get(index);
    return aEstate;
  }

  public List<Estate> getEstates()
  {
    List<Estate> newEstates = Collections.unmodifiableList(estates);
    return newEstates;
  }

  public int numberOfEstates()
  {
    int number = estates.size();
    return number;
  }

  public boolean hasEstates()
  {
    boolean has = estates.size() > 0;
    return has;
  }

  public int indexOfEstate(Estate aEstate)
  {
    int index = estates.indexOf(aEstate);
    return index;
  }
  /* Code from template association_GetMany */
  public Die getDy(int index)
  {
    Die aDy = dies.get(index);
    return aDy;
  }

  public List<Die> getDies()
  {
    List<Die> newDies = Collections.unmodifiableList(dies);
    return newDies;
  }

  public int numberOfDies()
  {
    int number = dies.size();
    return number;
  }

  public boolean hasDies()
  {
    boolean has = dies.size() > 0;
    return has;
  }

  public int indexOfDy(Die aDy)
  {
    int index = dies.indexOf(aDy);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHand()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHand(Card aHand)
  {
    boolean wasAdded = false;
    if (hand.contains(aHand)) { return false; }
    hand.add(aHand);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHand(Card aHand)
  {
    boolean wasRemoved = false;
    if (hand.contains(aHand))
    {
      hand.remove(aHand);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHandAt(Card aHand, int index)
  {  
    boolean wasAdded = false;
    if(addHand(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHandAt(Card aHand, int index)
  {
    boolean wasAdded = false;
    if(hand.contains(aHand))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHand()) { index = numberOfHand() - 1; }
      hand.remove(aHand);
      hand.add(index, aHand);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHandAt(aHand, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEstates()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addEstate(Estate aEstate)
  {
    boolean wasAdded = false;
    if (estates.contains(aEstate)) { return false; }
    estates.add(aEstate);
    if (aEstate.indexOfPlayersInEstate(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aEstate.addPlayersInEstate(this);
      if (!wasAdded)
      {
        estates.remove(aEstate);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeEstate(Estate aEstate)
  {
    boolean wasRemoved = false;
    if (!estates.contains(aEstate))
    {
      return wasRemoved;
    }

    int oldIndex = estates.indexOf(aEstate);
    estates.remove(oldIndex);
    if (aEstate.indexOfPlayersInEstate(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aEstate.removePlayersInEstate(this);
      if (!wasRemoved)
      {
        estates.add(oldIndex,aEstate);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEstateAt(Estate aEstate, int index)
  {  
    boolean wasAdded = false;
    if(addEstate(aEstate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEstates()) { index = numberOfEstates() - 1; }
      estates.remove(aEstate);
      estates.add(index, aEstate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEstateAt(Estate aEstate, int index)
  {
    boolean wasAdded = false;
    if(estates.contains(aEstate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEstates()) { index = numberOfEstates() - 1; }
      estates.remove(aEstate);
      estates.add(index, aEstate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEstateAt(aEstate, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfDies()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addDy(Die aDy)
  {
    boolean wasAdded = false;
    if (dies.contains(aDy)) { return false; }
    dies.add(aDy);
    if (aDy.indexOfPlayer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aDy.addPlayer(this);
      if (!wasAdded)
      {
        dies.remove(aDy);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeDy(Die aDy)
  {
    boolean wasRemoved = false;
    if (!dies.contains(aDy))
    {
      return wasRemoved;
    }

    int oldIndex = dies.indexOf(aDy);
    dies.remove(oldIndex);
    if (aDy.indexOfPlayer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aDy.removePlayer(this);
      if (!wasRemoved)
      {
        dies.add(oldIndex,aDy);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addDyAt(Die aDy, int index)
  {  
    boolean wasAdded = false;
    if(addDy(aDy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDies()) { index = numberOfDies() - 1; }
      dies.remove(aDy);
      dies.add(index, aDy);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveDyAt(Die aDy, int index)
  {
    boolean wasAdded = false;
    if(dies.contains(aDy))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfDies()) { index = numberOfDies() - 1; }
      dies.remove(aDy);
      dies.add(index, aDy);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addDyAt(aDy, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    hand.clear();
    ArrayList<Estate> copyOfEstates = new ArrayList<Estate>(estates);
    estates.clear();
    for(Estate aEstate : copyOfEstates)
    {
      aEstate.removePlayersInEstate(this);
    }
    ArrayList<Die> copyOfDies = new ArrayList<Die>(dies);
    dies.clear();
    for(Die aDy : copyOfDies)
    {
      aDy.removePlayer(this);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "turn" + ":" + getTurn()+ "]";
  }
}