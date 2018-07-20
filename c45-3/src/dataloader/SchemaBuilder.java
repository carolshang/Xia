package dataloader;
import java.io.BufferedReader;
import java.io.FileReader;

import tools.Const;
import entity.Schema;
public class SchemaBuilder {
	public Schema BuildSchema(String FileName) throws Exception{
		//读取属性文件
		BufferedReader reader = new BufferedReader(new FileReader(FileName));
		Schema result = new Schema();
		//属性存入数据
		String Titles[] = reader.readLine().split(",");
		//添加到模型中
		for (String s:Titles)
			result.addAttributesName(s);
		String buffer = "";
		//获取属性对应的取值
		while((buffer=reader.readLine())!=null){
			if(buffer.contains("?"))
				continue;
			String[] val = buffer.split(",");
			//考虑到属性类别取值中存在数字类型
			for(String s:val){
				if(s.contains("."))
					result.addType(Const.DOUBLE);
				else
					result.addType(Const.INTEGER);
			}
			break;
		}
		//TODO 属性值类型
		for(int i=0;i<result.length();i++){
			result.addDataType(Const.DISCRETE);
		}
		reader.close();
		return result;
	}
	
}
