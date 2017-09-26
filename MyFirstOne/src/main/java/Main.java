import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


class Main{
    public static void main(String[] args) throws IOException {
        String url = "https://rozetka.com.ua/ua/mobile-cases/c146229/filter/";
        parse_category(url);
    }
    private static void parse_category(String url) throws IOException{
        StringBuilder sb = new StringBuilder();
        int maxim = 0;
        int maxtry;
        String inform = "";
        for(int i = 1; i < 6; i++){
            Document doc = Jsoup.connect("https://rozetka.com.ua/ua/mobile-cases/c146229/filter/page=" +
                    Integer.toString(i)).get();
            Elements tiles = doc.select("div[class*=g-i-tile-i-title]");
            for(Element tile:tiles) {
                Elements nah = tile.select("a[href]");
                String op = nah.attr("href");
                String parser = parse_reviews(op + "comments/");
                maxtry = Integer.parseInt(parser.substring(0, parser.indexOf(" ")));
                if(maxtry > maxim){
                    maxim = maxtry;
                    inform = "\n" + tile.text() + " " + parser;
                }
                System.out.print("\n" + tile.text() + " " + parser);
                sb.append(tile.text() + " " + parser + "\n");
            }
        }
        PrintWriter pw = new PrintWriter(new File("cases.csv"));
        pw.write(sb.toString());
        pw.close();
        System.out.print("\n*********\nMax number of reviews had:" + inform);
    }
    private static String parse_reviews(String url) throws IOException{
        Document meg = Jsoup.connect(url).get();
        Elements nums = meg.select("a[class*=paginator-catalog-l-link]");
        int num = 1;
        for(Element n:nums){
            num += 1;
            //System.out.print(n);
        }
        int nu;
        int coms = 0;
        for(nu = 1; nu < num + 1; nu++){
            String pg = url + "page=" + nu + "/";
            //System.out.print("\n" + pg);
            coms += parse_reviews_page(pg);

        }
        //System.out.print("\n" + coms + " reviews from " + url);
        return coms + " reviews from " + url;
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