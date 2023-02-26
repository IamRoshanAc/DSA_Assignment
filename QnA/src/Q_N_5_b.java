//5b)
//Assume an electric vehicle must go from source city s to destination city d. You can locate many service centers along
// the journey that allow for the replacement of batteries; however, each service center provides batteries
// with a specific capacity. You are given a 2D array in which servicecenter[i]=[xi,yj] indicates that
// the ith service center is xi miles from the source city and offers yj miles after the automobile can travel after
// replacing batteries at specific service centers. Return the number of times the car's batteries need to be replaced
// before arriving at the destination.
//Input: serviceCenters = [{10,60},{20,30},{30,30},{60,40}], targetMiles= 100, startChargeCapacity = 10
//Output: 2
//Explanation: The car can go 10 miles on its initial capacity; after 10 miles, the car replaces batteries with a capacity of 60 miles; and after travelling 50 miles, at position 60 we change batteries with a capacity of 40 miles; and ultimately, we may arrive at our destination.


import java.util.Arrays;
import java.util.Comparator;

public class Q_N_5_b {

    public static void main(String[] args) {
        int[][] serviceCenters = {{10,60},{20,30},{30,30},{60,40}};
        int targetMiles = 100;
        int startChargeCapacity = 10;
        int numBatteryReplacements = batteryReplacements(serviceCenters, targetMiles, startChargeCapacity);
        System.out.println(numBatteryReplacements);
    }

    public static int batteryReplacements(int[][] serviceCenters, int targetMiles, int startChargeCapacity) {
        int numBatteryReplacements = 0;
        int currentMiles = 0;
        int currentChargeCapacity = startChargeCapacity;

        // Sort service centers by distance from source city
        Arrays.sort(serviceCenters, Comparator.comparingInt(sc -> sc[0]));

        for (int i = 0; i < serviceCenters.length; i++) {
            int[] currentCenter = serviceCenters[i];
            int distanceToNextCenter = i == serviceCenters.length - 1 ? targetMiles - currentMiles : serviceCenters[i+1][0] - currentCenter[0];

            // Calculate the number of battery replacements required to reach the next service center
            int batteryReplacementsNeeded = (int) Math.ceil((double) distanceToNextCenter / currentChargeCapacity) - 1;

            // If we can't make it to the next center, then we need to stop and recharge
            if (batteryReplacementsNeeded > 0 && currentChargeCapacity * batteryReplacementsNeeded < distanceToNextCenter) {
                numBatteryReplacements += batteryReplacementsNeeded;
                currentChargeCapacity = currentCenter[1];
            }

            // Update our current miles and charge capacity
            currentMiles += distanceToNextCenter;
            currentChargeCapacity -= distanceToNextCenter;
            if (currentChargeCapacity <= 0) {
                numBatteryReplacements++;
                currentChargeCapacity = currentCenter[1] - (distanceToNextCenter - startChargeCapacity);
            }

            // If we've reached the target miles, then we're done
            if (currentMiles == targetMiles) {
                return numBatteryReplacements;
            }
        }

        // If we didn't reach the target miles, then we need to keep going until we do
        int distanceToTarget = targetMiles - currentMiles;
        int batteryReplacementsNeeded = (int) Math.ceil((double) distanceToTarget / currentChargeCapacity) - 1;
        numBatteryReplacements += batteryReplacementsNeeded;

        return numBatteryReplacements;
    }



}
