package model;

import java.util.ArrayList;

public class CheckInfo {
public static boolean check(PersonalCertInfo info){
	ArrayList<PersonalCertInfo> list = CertificateDAO.getPersonInfoList();
	for(PersonalCertInfo p : list){
		if(p.equals(info)) return true;
		
	}
	return false;
}
public static boolean check(IncorporationCertInfo info){
	ArrayList<IncorporationCertInfo> list = CertificateDAO.getIncorpInfoList();
	for(IncorporationCertInfo p : list){
		if(p.equals(info)) return true;
	}
	return false;
}
}
