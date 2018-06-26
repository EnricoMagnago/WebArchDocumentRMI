package it.unitn.webarch;

/**
 * Project: Assignment_5
 * Created by en on 01/11/17.
 */

import javax.ejb.Remote;
import javax.ejb.Stateless;
import java.text.SimpleDateFormat;
import java.util.Date;


@Stateless
@Remote(CurrentTime.class)
public class CurrentTimeBean implements CurrentTime{
	private static final long serialVersionUID = 1L;
	public String getCurrentTime(){
		final SimpleDateFormat df = new SimpleDateFormat("dd,MM,yyyy HH:mm:ss");
		return df.format(new Date());
	}
}
