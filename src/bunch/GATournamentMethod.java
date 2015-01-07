/*   1:    */ package bunch;
/*   2:    */ 
/*   3:    */ import java.util.Random;
/*   4:    */ 
/*   5:    */ public class GATournamentMethod
/*   6:    */   extends GAMethod
/*   7:    */ {
/*   8:    */   protected int[] tempArray_d;
/*   9:    */   
/*  10:    */   public void init()
/*  11:    */   {
/*  12: 41 */     setIncrementCounter(1);
/*  13: 42 */     setInitCounter(0);
/*  14: 43 */     setMaxCounter(this.currentPopulation_d.length);
/*  15: 44 */     this.tempArray_d = new int[this.currentPopulation_d[0].getClusters().length];
/*  16:    */   }
/*  17:    */   
/*  18:    */   public void selectReproduceCrossAndMutate(int pos)
/*  19:    */   {
/*  20: 56 */     Graph parent1 = this.currentPopulation_d[((int)((this.currentPopulation_d.length - 1) * this.randomGenerator_d.nextFloat()))];
/*  21: 57 */     Graph parent2 = this.currentPopulation_d[((int)((this.currentPopulation_d.length - 1) * this.randomGenerator_d.nextFloat()))];
/*  22:    */     
/*  23: 59 */     int[] p1c = parent1.getClusters();
/*  24: 60 */     int[] p2c = parent2.getClusters();
/*  25: 62 */     if (parent1.getObjectiveFunctionValue() < parent2.getObjectiveFunctionValue())
/*  26:    */     {
/*  27: 64 */       p1c = parent2.getClusters();
/*  28: 65 */       p2c = parent1.getClusters();
/*  29:    */     }
/*  30: 68 */     int[] np1c = this.newPopulation_d[pos].getClusters();
/*  31:    */     
/*  32: 70 */     System.arraycopy(p1c, 0, np1c, 0, p1c.length);
/*  33: 73 */     if (this.randomGenerator_d.nextFloat() < this.crossoverThreshold_d)
/*  34:    */     {
/*  35: 74 */       int crossp = (int)(this.randomGenerator_d.nextFloat() * (np1c.length - 1));
/*  36: 75 */       cross(np1c, p2c, crossp);
/*  37:    */     }
/*  38: 79 */     for (int i = 0; i < np1c.length; i++) {
/*  39: 80 */       if (this.randomGenerator_d.nextFloat() < this.mutationThreshold_d) {
/*  40: 81 */         mutate(np1c, i);
/*  41:    */       }
/*  42:    */     }
/*  43:    */   }
/*  44:    */   
/*  45:    */   public void mutate(int[] c, int pos)
/*  46:    */   {
/*  47: 93 */     c[pos] = ((int)(this.randomGenerator_d.nextFloat() * (c.length - 1)));
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void cross(int[] c1c, int[] c2c, int crossp)
/*  51:    */   {
/*  52:103 */     System.arraycopy(c2c, crossp, c1c, crossp, c1c.length - crossp);
/*  53:    */   }
/*  54:    */ }


/* Location:           C:\Users\Joon\Desktop\Bunch-3.5\
 * Qualified Name:     bunch.GATournamentMethod
 * JD-Core Version:    0.7.0.1
 */