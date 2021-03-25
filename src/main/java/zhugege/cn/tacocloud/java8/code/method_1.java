package zhugege.cn.tacocloud.java8.code;

import java.util.Arrays;

public class method_1 {

    public static void main(String[] args){


    }

}

class Solution {
    public int rob(int[] nums) {

        int N = nums.length;
        int max = 0;
        for(int i=0;i<N;i++){
            if(nums[max] > nums[i]){
                max = i;
            }
        }
        if(max == 0){

        }
        int rob1 = rob(Arrays.copyOfRange(nums,1,N));
        int rob2 = dfs(Arrays.copyOfRange(nums,0,N-1));
        return Math.max(rob1,rob2);
    }

    public int dfs(int[] nums){
        int N = nums.length;
        int[] dp = new int[N];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);
        for(int i=2;i<N;i++){
            dp[i] = Math.max(dp[i-1],dp[i-2] + nums[i]);
        }
        return dp[N-1];
    }
}
