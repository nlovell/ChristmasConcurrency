package machine;

public class MachinePart {
    final String ID;

    public MachinePart(final String id) {
        this.ID = id;
    }

    public MachinePart(final int id){
        this.ID = String.valueOf(id);
    }

    public String getId(){
        return ID;
    }
}