package jp.thinq.solr.normalization;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.text.Normalizer;

import org.apache.lucene.analysis.BaseCharFilter;
import org.apache.lucene.analysis.CharReader;
import org.apache.lucene.analysis.CharStream;

public class NormalizeCharFilter extends BaseCharFilter {

	public static void main(String[] args) throws IOException {
		CharStream in = CharReader.get(new StringReader("らんにんぐ㌦ＡＢＣdef３㎞をﾗﾝﾆﾝｸﾞしましたαβ"));
		NormalizeCharFilter f = new NormalizeCharFilter(Normalizer.Form.NFKC, in);
		char[] cb = new char[2048]; 
		int len = f.read(cb, 0, 2048);
		String s = new String(cb, 0, len);
		System.out.println(s);
		System.out.println(s.equals("らんにんぐドルABCdef3kmをランニングしましたαβ"));
	}
	public NormalizeCharFilter(Normalizer.Form form, CharStream in) {
		super(in);
		input = normalize(form, input);
	}
	private CharStream normalize(Normalizer.Form form, CharStream in){
		StringBuffer sb = new StringBuffer();
		char[] cb = new char[1024];
		int len;
		try {
			while ((len = in.read(cb)) > 0) {
				sb.append(new String(cb, 0, len));
			}
		} catch (IOException e) {
			// stop reading if exception occured.
		}
		Reader sr = new StringReader(Normalizer.normalize(sb, form));
		return CharReader.get(sr);
	}
}
