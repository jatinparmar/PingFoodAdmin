package project.com.cebs.pingfoodsadmin;

/**
 * Created by Jatin on 30-07-2017.
 */

public class ModelAddRestaurant
{
    private String restaurant_name,restaurant_description,tagline1,taglin2,website;

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWebsite() {
        return website;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public void setRestaurant_description(String restaurant_description) {
        this.restaurant_description = restaurant_description;
    }

    public void setTagline1(String tagline1) {
        this.tagline1 = tagline1;
    }

    public void setTaglin2(String taglin2) {
        this.taglin2 = taglin2;
    }

    public String getRestaurant_description() {
        return restaurant_description;
    }

    public String getTagline1() {
        return tagline1;
    }

    public String getTaglin2() {
        return taglin2;
    }
}
