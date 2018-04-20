package com.hzw.recommand;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author: Hzw
 * @Time: 2018/4/20 10:56
 * @Description:
 */
public class ItemBased {

    final static int NEIGHBORHOOD_NUM = 2;
    final static int RECOMMENDER_NUM = 3;

    public static void main(String[] args) throws IOException,TasteException
    {
        RandomUtils.useTestSeed();
        String file = "datafile/data.csv";

        DataModel model = new FileDataModel(new File(file));

        ItemSimilarity item = new EuclideanDistanceSimilarity(model);

        Recommender r = new GenericItemBasedRecommender(model,item);

        LongPrimitiveIterator iter = model.getUserIDs();

        while(iter.hasNext())
        {
            long uid = iter.nextLong();
            List<RecommendedItem> list = r.recommend(uid,RECOMMENDER_NUM);  //获取推荐结果
            System.out.printf("uid:%s",uid);
            //遍历推荐结果
            for(RecommendedItem ritem : list)
            {
                System.out.printf("(%s,%f)",ritem.getItemID(),ritem.getValue());
            }
            System.out.println();
        }

    }
}
