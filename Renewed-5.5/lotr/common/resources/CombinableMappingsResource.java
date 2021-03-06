package lotr.common.resources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class CombinableMappingsResource {
   protected final Map mappings;
   private final int loadOrder;
   private final int numCombinedFrom;

   public CombinableMappingsResource(Map mappings, int loadOrder, int numCombinedFrom) {
      this.mappings = mappings;
      this.loadOrder = loadOrder;
      this.numCombinedFrom = numCombinedFrom;
   }

   private int getLoadOrder() {
      return this.loadOrder;
   }

   public final int getNumCombinedFrom() {
      return this.numCombinedFrom;
   }

   public final int size() {
      return this.mappings.size();
   }

   public static CombinableMappingsResource combine(List resources, CombinableMappingsResource.CombinableMappingsResourceFactory factory) {
      List sorted = new ArrayList(resources);
      Collections.sort(sorted, Comparator.comparingInt(CombinableMappingsResource::getLoadOrder));
      Map mappings = new HashMap();
      Iterator var4 = sorted.iterator();

      while(var4.hasNext()) {
         CombinableMappingsResource table = (CombinableMappingsResource)var4.next();
         mappings.putAll(table.mappings);
      }

      return factory.create(mappings, 0, sorted.size());
   }

   @FunctionalInterface
   public interface CombinableMappingsResourceFactory {
      CombinableMappingsResource create(Map var1, int var2, int var3);
   }
}
