package com.ktnet.common.util;

import java.util.HashMap;


public class MimeType {

    private final String[] aMimes = { "application/andrew-inset,ez",
            "application/atom+xml,atom", "application/mac-binhex40,hqx",
            "application/mac-compactpro,cpt", "application/mathml+xml,mathml",
            "application/msword,doc",
            "application/octet-stream,bin,dms,lha,lzh,exe,class,so,dll,dmg",
            "application/oda,oda", "application/ogg,ogg",
            "application/pdf,pdf", "application/postscript,ai,eps,ps",
            "application/rdf+xml,rdf", "application/smil,smi,smil",
            "application/srgs,gram", "application/srgs+xml,grxml",
            "application/vnd.mif,mif", "application/vnd.mozilla.xul+xml,xul",
            "application/vnd.ms-excel,xls",
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,xlsx",
            "application/vnd.ms-powerpoint,ppt",
            "application/vnd.openxmlformats-officedocument.presentationml.presentation,pptx",
            "application/vnd.wap.wbxml,wbxml", "application/vnd.wap.wmlc,wmlc",
            "application/vnd.wap.wmlscriptc,wmlsc",
            "application/x-bcpio,bcpio", "application/x-cdlink,vcd",
            "application/x-chess-pgn,pgn", "application/x-cpio,cpio",
            "application/x-csh,csh", "application/x-director,dcr,dir,dxr",
            "application/x-dvi,dvi", "application/x-futuresplash,spl",
            "application/x-gtar,gtar", "application/x-hdf,hdf",
            "application/x-hwp,hwp", "application/x-javascript,js",
            "application/x-koan,skp,skd,skt,skm", "application/x-latex,latex",
            "application/x-netcdf,nc,cdf", "application/x-sh,sh",
            "application/x-shar,shar", "application/x-shockwave-flash,swf",
            "application/x-stuffit,sit", "application/x-sv4cpio,sv4cpio",
            "application/x-sv4crc,sv4crc", "application/x-tar,tar",
            "application/x-tcl,tcl", "application/x-tex,tex",
            "application/x-texinfo,texinfo,texi",
            "application/x-troff,t,tr,roff", "application/x-troff-man,man",
            "application/x-troff-me,me", "application/x-troff-ms,ms",
            "application/x-ustar,ustar", "application/x-wais-source,src",
            "application/xhtml+xml,xhtml,xht", "application/xslt+xml,xslt",
            "application/xml,xml,xsl", "application/xml-dtd,dtd",
            "application/xml-external-parsed-entity", "application/zip,zip",
            "audio/basic,au,snd", "audio/midi,mid,midi,kar",
            "audio/mpeg,mpga,mp2,mp3", "audio/x-aiff,aif,aiff,aifc",
            "audio/x-mpegurl,m3u", "audio/x-pn-realaudio,ram,ra",
            "application/vnd.rn-realmedia,rm", "audio/x-wav,wav",
            "chemical/x-pdb,pdb", "chemical/x-xyz,xyz", "image/bmp,bmp",
            "image/cgm,cgm", "image/gif,gif", "image/ief,ief",
            "image/jpeg,jpeg,jpg,jpe", "image/png,png", "image/svg+xml,svg",
            "image/tiff,tiff,tif", "image/vnd.djvu,djvu,djv",
            "image/vnd.wap.wbmp,wbmp", "image/x-cmu-raster,ras",
            "image/x-icon,ico", "image/x-portable-anymap,pnm",
            "image/x-portable-bitmap,pbm", "image/x-portable-graymap,pgm",
            "image/x-portable-pixmap,ppm", "image/x-rgb,rgb",
            "image/x-xbitmap,xbm", "image/x-xpixmap,xpm",
            "image/x-xwindowdump,xwd", "model/iges,igs,iges",
            "model/mesh,msh,mesh,silo", "model/vrml,wrl,vrml",
            "ext/calendar,ics,ifb", "text/css,css", "text/html,html,htm",
            "text/plain,asc,txt", "text/richtext,rtx", "text/rtf,rtf",
            "text/sgml,sgml,sgm", "text/tab-separated-values,tsv",
            "text/vnd.wap.wml,wml", "text/vnd.wap.wmlscript,wmls",
            "text/x-setext,etx", "video/mpeg,mpeg,mpg,mpe",
            "video/quicktime,qt,mov", "video/vnd.mpegurl,mxu,m4u",
            "video/x-msvideo,avi", "video/x-sgi-movie,movie",
            "x-conference/x-cooltalk,ice" };

	private HashMap<String, String> mimemap = new HashMap<String, String>();
	private static MimeType instance = new MimeType();

	private MimeType() {
		String[] at = null;
		String mime = null;
		for (int i = 0; i < aMimes.length; i++) {
			at = aMimes[i].split(",");
			mime = at[0];
			for (int j = 1; j < at.length; j++) {
				mimemap.put(at[j], mime);
			}
		}
	}

	public static String getMimeType(String fileName) {
		String m = null;
		if (fileName != null) {
			String ext = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

			m = (String) instance.mimemap.get(ext);
		}
		if (m == null) {
			m = "file/unknown";
		}
		return m;

	}
}