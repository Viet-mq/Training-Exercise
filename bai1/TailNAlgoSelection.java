public class TailNAlgoSelection {

    public TailNAlgoSelection() {

    }

    public static TailN selectAlgo(String option){
        if(option.equals("normal")){
            return new TailN();
        }else return null;
    }
}


