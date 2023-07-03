package ReservationProgram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TableDataFileManager {
	private String fileName;
	TableData[] tablesDatas;

	public Boolean LoadDataFile(String filePath) {
		this.fileName = filePath;
		boolean fileIsFound = false;
		try {
			File file = new File(fileName);
			if (file.exists()) {
				Scanner sc = new Scanner(file, "UTF-8");
				if (sc.hasNext()) {
					int tableNumber = sc.nextInt();
					tablesDatas = new TableData[tableNumber];
					int tableIndex = 0;
					fileIsFound = true;
					for (int i = 0; i < tablesDatas.length + 1; i++) {
						String s = sc.nextLine();
						if (i == 0) {
							continue;
						}
						String[] parts = s.split(" ");
						TableData tableData = new TableData();
						tableData.tableNum = Integer.parseInt(parts[0]);
						tableData.accommodatablePeople = Integer.parseInt(parts[1]);
						tablesDatas[tableIndex] = tableData;
						tableIndex++;

					}
				} else {
					// System.out.println("\nFile Is Empty!");
				}
				sc.close();

			} else {

				file.createNewFile();
				System.out.println("\n파일이 존재하지 않습니다. 파일을 새로 생성하였습니다.");
				return true;

			}

		} catch (FileNotFoundException e) {
			System.out.println("\nFile not Found");

		} catch (IOException e) {
			System.out.println("\nException");
		}

		// System.out.println("\n파일 불러오기 성공");

		return true;
	}

	public TableData[] getTableData() {

		return tablesDatas;
	}

	public int getNumberOfTables() {

		return tablesDatas.length;
	}
}
