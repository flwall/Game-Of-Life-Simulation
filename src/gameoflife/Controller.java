
package gameoflife;

public class Controller {
    
    
    private View v;
    private Model m;
    
    public Controller(){
        
        this.v=new View(this);
        this.m=new Model();
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
