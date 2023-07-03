package ReservationProgram;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;

public class RestaurantDataFileManager {
	public static final int RestaurantDataFile_recordSize = 3;
	
	private ArrayList<RestaurantData> records = new ArrayList<>();
	
	public Boolean LoadDataFile() {
		records.clear();
		File file = new File("Restaurant.txt");
		Scanner sc;
		try {
			sc = new Scanner(file, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			try {
				file.createNewFile();
				sc = new Scanner(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("Restaurant.txt 파일 생성 도중 오류가 발생했습니다:");
				e1.printStackTrace();
				return false;
			}
		}
		String[] record = new String[RestaurantDataFile_recordSize];
		int i_line=0;
		while(sc.hasNextLine()==true) {
			record[i_line%record.length]=sc.nextLine();
			if(i_line%3==record.length-1) {
				if(this.AddRestaurantData(record[0],record[1],record[2], false)==false) {
					System.out.println("RestaurantDataFileManager 객체의 AddRestaurantData 메소드 실행 도중 오류가 발생했습니다.");
					sc.close();
					return false;
				}
			}
			i_line++;
		}
		if(i_line%RestaurantDataFile_recordSize!=0) {
			System.out.println("Restaurant.txt 데이터 파일을 읽는 중 오류가 발생했습니다: 파일의 줄 수가 데이터의 개수로 나누어떨어지지 않습니다.");
			sc.close();
			return false;
		}
		sc.close();
		return true;
	}
	
	public Boolean AddRestaurantData(String restaurantName, String restaurantpw, String menuInfoStr, boolean b) {
		File DataFile = new File("Restaurant.txt");
		RestaurantData data;
		try {
			data = new RestaurantData(restaurantName, restaurantpw, this.StringToMenuInfo(menuInfoStr));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			return false;
		}
		records.add(data);
		if(b==false) {
			return true;
		}
		else {
//			FileWriter Writer;
			BufferedWriter Writer;
			try {
//				Writer = new FileWriter(DataFile, true);
				Writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(DataFile, true), StandardCharsets.UTF_8));

				String str = "";
				str += restaurantName + "\r\n";
				str += restaurantpw + "\r\n";
				str += menuInfoStr + "\r\n";
			
				Writer.write(str);
				Writer.flush();
				Writer.close();
				return true;
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				return false;
			}

		}
	}

	private MenuInfo StringToMenuInfo(String menuInfoStr) throws ParseException {
		// TODO Auto-generated method stub
		if(menuInfoStr.equals("")) {
			MenuInfo result = new MenuInfo(new String[0], new int[0]);
			return result;
		}
		String[] record = menuInfoStr.split("\t");
		MenuInfo result = new MenuInfo(new String[record.length],new int[record.length]);
		for(int i=0;i<record.length;i++) {
			String[] nameAndPrice = record[i].split("=");
			if(nameAndPrice.length!=2) {
				throw new ParseException("RestaurantDataFileManager 객체의 StringToMenuInfo 메소드에서 오류 발생: record["+i+"]를 split한 결과의 length가 2와 일치하지 않습니다.", 0);
			}
			result.menuName[i]=nameAndPrice[0];
			try {
				result.menuPrice[i]=Integer.parseInt(nameAndPrice[1]);
			}
			catch(NumberFormatException e) {
				throw new ParseException("RestaurantDataFileManager 객체의 StringToMenuInfo 메소드에서 오류 발생: Integer.parseInt를 호출하는 과정에서 오류가 발생했습니다.", 0);
			}
		}
		return result;
	}

	public OrderedMenuInfo StringToOrderedMenuInfo(String str) throws ParseException{
		String[] record = str.split("\t");
		OrderedMenuInfo result = new OrderedMenuInfo(new int[record.length],new int[record.length]);
		for(int i=0;i<record.length;i++) {
			String[] menuAndAmount = record[i].split("\\s");
			if(menuAndAmount.length!=2) {
				throw new ParseException("RestaurantDataFileManager 객체의 StringToOrderedMenuInfo 메소드에서 오류 발생: record["+i+"]를 split한 결과의 length가 2와 일치하지 않습니다.", 0);
			}
			try {
				result.menuIndex[i]=Integer.parseInt(menuAndAmount[0]);
				result.orderedAmount[i]=Integer.parseInt(menuAndAmount[1]);
			}
			catch(NumberFormatException e) {
				throw new ParseException("RestaurantDataFileManager 객체의 StringToOrderedMenuInfo 메소드에서 오류 발생: Integer.parseInt를 호출하는 과정에서 오류가 발생했습니다.", 0);
			}
		}
		ArrayList<Integer[]> tempList = new ArrayList<>();
		HashMap<Integer, Integer> indexMap = new HashMap<>();
		for(int i=0;i<result.menuIndex.length;i++) {
			if(indexMap.containsKey(result.menuIndex[i])) { 
				tempList.get(indexMap.get(result.menuIndex[i]))[1] += result.orderedAmount[i];
			}
			else {
				tempList.add(new Integer[] {result.menuIndex[i], result.orderedAmount[i]});
				indexMap.put(result.menuIndex[i], tempList.size()-1);
			}	//value : tempList 상에서의 인덱스, key : menuIndex 값 
		}
		Comparator<Integer[]> cmp = (o1, o2)->o1[0].compareTo(o2[0]);
		Collections.sort(tempList, cmp);
		
		result.menuIndex = new int[tempList.size()];
		result.orderedAmount = new int[tempList.size()];
		for(int i=0;i<tempList.size();i++) {
			result.menuIndex[i] = tempList.get(i)[0];
			result.orderedAmount[i] = tempList.get(i)[1];
		}
		
		return result;
	}
	
	public ArrayList<RestaurantData> getRestaurantData(){
		return this.records;
	}

}
