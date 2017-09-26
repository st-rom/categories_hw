import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;


class Main{
    public static void main(String[] args) throws IOException {
        String url = "https://rozetka.com.ua/ua/mobile-cases/c146229/filter/";
        parse_category(url);
    }
    private static void parse_category(String url) throws IOException{
        //Document doc = Jsoup.connect("https://rozetka.com.ua/ua/mobile-cases/c146229/filter/page=1").get();
        //System.out.print(doc);
        //Elements ss = doc.select("title");
        //String first = ss.text().substring(0, 30);
        //System.out.print("\n" + first);
        //Elements em = doc.select("a[class*=paginator-catalog-l-link]");
        //Elements tiles = doc.select("div[class*=g-i-tile-i-title]");
        for(int i = 1; i < 6; i++){
            Document doc = Jsoup.connect("https://rozetka.com.ua/ua/mobile-cases/c146229/filter/page=" +
                    Integer.toString(i)).get();
            Elements tiles = doc.select("div[class*=g-i-tile-i-title]");
            for(Element tile:tiles) {
                //System.out.print(tile);
                Elements nah = tile.select("a[href]");
                String op = nah.attr("href");
                System.out.print("\n" + tile.text() + parse_reviews(op + "comments/"));
                //System.out.print("\n" + "");
                //Elements as = tile.select("a[class*=href]");// + "comments/");

                //System.out.print("\n" + op + "comment/");

            }
        }
    }
    private static String parse_reviews(String url) throws IOException{
        Document meg = Jsoup.connect(url).get();
        Elements nums = meg.select("a[class*=paginator-catalog-l-link]");
        int num = 1;
        for(Element n:nums){
            num += 1;
            //System.out.print(n);
        }
        String []sents = new String[num];
        int nu;
        int coms = 0;
        for(nu = 1; nu < num + 1; nu++){
            String pg = url + "page=" + nu + "/";
            //System.out.print("\n" + pg);
            //sents[nu - 1] = parse_reviews_page(pg);
            coms += parse_reviews_page(pg);

        }
        //System.out.print("\n" + coms + " reviews from " + url);
        String kst = "\n" + coms + " reviews from " + url;
        return kst;


    }
    private  static int parse_reviews_page(String url) throws IOException{
        Document sup = Jsoup.connect(url).get();
        Elements reviews = sup.select("article[class*=pp-review-i]");
        int coms = 0;
        for(Element rev:reviews){
            Elements star = rev.select("span[class*=g-rating-stars-i]");
            Elements text = rev.select("div[class*=pp-review-text]");
            coms += 1;

        }
        return coms;
    }
}