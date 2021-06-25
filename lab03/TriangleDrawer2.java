public class TriangleDrawer2 {
    public static void main(String[] args){
        int size =10;
        for(int i=1;i<=size;i+=1){
            for(int j=0;j<i;j+=1){
                System.out.print('*');
            }
            System.out.println();
        }
    }
}
