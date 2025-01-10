import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class WatchServiceExample{
	static Path path = null;
	static WatchService watchService =null;
	
	static void init() {
		try {
			path = Paths.get("C:\\temp");
			watchService = FileSystems.getDefault().newWatchService();
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void doRounds() {
		WatchKey key = null;
		while(true) {
			try {
				key = watchService.take();
				
				for(WatchEvent<?> event : key.pollEvents()) {
					Kind<?> kind = event.kind();
					System.out.println("Event on " + event.context().toString() +" is " + kind);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			boolean reset = key.reset();
			if(!reset)
				break;
			
		}
	}
	public static void main(String[] args) {
		Test t = new Test();
		t.init();
		t.doRounds();
		
	}

}
