

package gameoflife;

public class Controller {
    
    
    private View v;
    private Model m;
    
    public Controller(){
        this.m = new Model();
        this.v=new View(this);
        
        m.addObserver(v);
    }

     public View getView() {
         return v;
    }
     public Model getModel()
     {
         return m;
     }

    
}
