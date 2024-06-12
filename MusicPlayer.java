import java.util.*;

class MusicPlayer {
    private static DoublyLinkedList<String> playlist = new DoublyLinkedList<>();
    private static int currentSongIndex = -1;
    private static boolean isPlaying = false;
    private static int volume = 50;
    private static List<String> recentlyPlayed = new ArrayList<>();

    public static void main(String[] args) {
        // Add songs to the Music Player
        addSongs(
            "Cham Cham",
            "Husn",
            "Jab Tak",
            "Ghoomar",
            "Genda Phool",
            "Breathless",
            "Masakali",
            "Mulaqat",
            "Kabira",
            "Khalasi"
        );

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a song");
            System.out.println("2. Play a random song");
            System.out.println("3. Skip forward");
            System.out.println("4. Skip backward");
            System.out.println("5. Adjust volume");
            System.out.println("6. Play/Pause");
            System.out.println("7. Display playlist");
            System.out.println("8. Sort playlist based on recently played");
            System.out.println("0. Exit");
            System.out.print("\nYour choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addSong(scanner);
                    break;
                case 2:
                    playRandomSong();
                    break;
                case 3:
                    skipForward();
                    break;
                case 4:
                    skipBackward();
                    break;
                case 5:
                    System.out.print("Enter new volume level (0-100): ");
                    int newVolume = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    adjustVolume(newVolume);
                    break;
                case 6:
                    playPause();
                    break;
                case 7:
                    displayPlaylist();
                    break;
                case 8:
                    sortPlaylistBasedOnRecentlyPlayed();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }

    static void addSongs(String... songsToAdd) {
        for (String song : songsToAdd) {
            playlist.add(song);
        }
    }

    static void addSong(Scanner scanner) {
        System.out.print("Enter song name: ");
        String songName = scanner.nextLine();
        playlist.add(songName);
        System.out.println("Song '" + songName + "' added successfully!");
    }

    static void playRandomSong() {
        Random random = new Random();
        int randomIndex;
        do {
            randomIndex = random.nextInt(playlist.size());
        } while (randomIndex == currentSongIndex); // Ensure not repeating the same song consecutively
        currentSongIndex = randomIndex;
        String randomSong = playlist.get(currentSongIndex);
        System.out.println("Playing random song: " + randomSong);
        recentlyPlayed.add(randomSong);
    }

    static void skipForward() {
        if (currentSongIndex < playlist.size() - 1) {
            currentSongIndex++;
            System.out.println("Skipping forward to next song: " + playlist.get(currentSongIndex));
        } else {
            System.out.println("No more songs available to skip forward.");
        }
    }

    static void skipBackward() {
        if (currentSongIndex > 0) {
            currentSongIndex--;
            System.out.println("Skipping backward to previous song: " + playlist.get(currentSongIndex));
        } else {
            System.out.println("No more songs available to skip backward.");
        }
    }

    static void adjustVolume(int newVolume) {
        volume = Math.max(0, Math.min(100, newVolume)); // Ensure volume is between 0 and 100
        System.out.println("Volume adjusted to: " + volume);
    }

    static void playPause() {
        isPlaying = !isPlaying;
        if (isPlaying) {
            System.out.println("Song is now playing.");
        } else {
            System.out.println("Song is paused.");
        }
    }

    static void displayPlaylist() {
        System.out.println("Current Playlist:");
        int index = 1;
        for (String song : playlist) {
            String status = (index == currentSongIndex + 1) ? " (Playing)" : "";
            System.out.println(index + ". " + song + status);
            index++;
        }
    }

    static void sortPlaylistBasedOnRecentlyPlayed() {
        List<String> playlistList = new ArrayList<>();
        for (String song : playlist) {
            playlistList.add(song);
        }
    
        Collections.sort(playlistList, (song1, song2) -> {
            int index1 = recentlyPlayed.indexOf(song1);
            int index2 = recentlyPlayed.indexOf(song2);
            if (index1 == -1) {
                return 1;
            } else if (index2 == -1) {
                return -1;
            } else {
                return Integer.compare(index1, index2);
            }
        });
    
        // Update the playlist with the sorted songs
        playlist = new DoublyLinkedList<>();
        for (String song : playlistList) {
            playlist.add(song);
        }
    
        System.out.println("Playlist sorted based on recently played songs.");
        displaySortedPlaylist();
    }
    static void displaySortedPlaylist() {
        System.out.println("Sorted Playlist based on recently played songs:");
        int index = 1;
        for (String song : playlist) {
            String status = (index == currentSongIndex + 1) ? " (Playing)" : "";
            System.out.println(index + ". " + song + status);
            index++;
        }
    }
    
    
    

    static class DoublyLinkedList<T> implements Iterable<T> {
        static class Node<T> {
            T data;
            Node<T> prev;
            Node<T> next;

            public Node(T data) {
                this.data = data;
            }
        }

        private Node<T> head;
        private Node<T> tail;
        private int size;

        public DoublyLinkedList() {
            head = null;
            tail = null;
            size = 0;
        }

        public void add(T data) {
            Node<T> newNode = new Node<>(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                tail.next = newNode;
                newNode.prev = tail;
                tail = newNode;
            }
            size++;
        }

        public T get(int index) {
            if (index < 0 || index >= size) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
            }

            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current.data;
        }

        public int size() {
            return size;
        }

        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private Node<T> current = head;

                @Override
                public boolean hasNext() {
                    return current != null;
                }

                @Override
                public T next() {
                    if (!hasNext()) {
                        throw new NoSuchElementException();
                    }
                    T data = current.data;
                    current = current.next;
                    return data;
                }
            };
        }
    }
}
