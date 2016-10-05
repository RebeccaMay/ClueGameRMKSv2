package clueGame;

public enum DoorDirection {
	UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');
	 
	private Character dir;
	DoorDirection(Character dir){
		this.dir = dir;
	}

	  public static DoorDirection fromCharacter(Character dir) {
	    if (dir != null) {
	      for (DoorDirection b : DoorDirection.values()) {
	        if (dir == b.dir) {
	          return b;
	        }
	      }
	    } 
	    return null;
	  }
	
}
