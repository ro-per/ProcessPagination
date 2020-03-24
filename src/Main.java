public class Main {
    public static void main(String[] args) {
        VMView view = new VMView();
        VMModel model = new VMModel();
        VMController controller = new VMController(view,model);

        //satge.resizableProperty().setValue(false); !!!!!!!! COMMENTAAR NIET WEGHALEN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                view.setVisible(true);
            }
        });
    }
}
