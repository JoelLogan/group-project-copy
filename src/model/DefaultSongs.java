package model;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import model.enums.KeyCodes;
import model.enums.SongDifficulties;

public final class DefaultSongs {

    public static final String SONGSLOCATION = "./src/data/";
    private static final String SONG1NAME = "Cinematic Experience.ser";
    private static final String SONG2NAME = "Funny Bubbles.ser";
    private static final String SONG3NAME = "Three Sheets To The Wind.ser";

    public static void saveDefaultSongs() {
        if (!checkFileExists(SONG1NAME)) {
            Serializer.serialize(song1(), SONGSLOCATION, SONG1NAME);
        }
        if (!checkFileExists(SONG2NAME)) {
            Serializer.serialize(song2(), SONGSLOCATION, SONG2NAME);
        }
        if (!checkFileExists(SONG3NAME)) {
            Serializer.serialize(song3(), SONGSLOCATION, SONG3NAME);
        }
    }

    private static boolean checkFileExists(String filename) {
        File f = new File(Path.of(SONGSLOCATION, filename).toString());
        if (f.isFile()) {
            return true;
        }
        return false;
    }

    public static Song song1() {
        List<Note> notes = new ArrayList<>(List.of(
                new Note(1647, 1043, KeyCodes.NUMPAD1),
                new Note(38176, 439, KeyCodes.NUMPAD1),
                new Note(48503, 659, KeyCodes.NUMPAD1),
                new Note(59983, 2087, KeyCodes.NUMPAD1),
                new Note(83878, 4394, KeyCodes.NUMPAD1),
                new Note(99093, 5273, KeyCodes.NUMPAD1),
                new Note(120296, 1483, KeyCodes.NUMPAD1),
                new Note(3460, 494, KeyCodes.NUMPAD2),
                new Note(19060, 4888, KeyCodes.NUMPAD2),
                new Note(27025, 2966, KeyCodes.NUMPAD2),
                new Note(47020, 1483, KeyCodes.NUMPAD2),
                new Note(83878, 4669, KeyCodes.NUMPAD2),
                new Note(98928, 3185, KeyCodes.NUMPAD2),
                new Note(127602, 4009, KeyCodes.NUMPAD2),
                new Note(5493, 2032, KeyCodes.NUMPAD3),
                new Note(23180, 1977, KeyCodes.NUMPAD3),
                new Note(42515, 4559, KeyCodes.NUMPAD3),
                new Note(69980, 3570, KeyCodes.NUMPAD3),
                new Note(75034, 4998, KeyCodes.NUMPAD3),
                new Note(89151, 933, KeyCodes.NUMPAD3),
                new Note(98819, 4998, KeyCodes.NUMPAD3),
                new Note(113869, 4119, KeyCodes.NUMPAD3),
                new Note(8569, 5547, KeyCodes.NUMPAD4),
                new Note(54600, 604, KeyCodes.NUMPAD4),
                new Note(62015, 2197, KeyCodes.NUMPAD4),
                new Note(68058, 4174, KeyCodes.NUMPAD4),
                new Note(79978, 4064, KeyCodes.NUMPAD4),
                new Note(89865, 769, KeyCodes.NUMPAD4),
                new Note(95193, 6152, KeyCodes.NUMPAD4),
                new Note(110354, 6866, KeyCodes.NUMPAD4),
                new Note(126613, 878, KeyCodes.NUMPAD4),
                new Note(134084, 2856, KeyCodes.NUMPAD4),
                new Note(8624, 5383, KeyCodes.NUMPAD5),
                new Note(19005, 4943, KeyCodes.NUMPAD5),
                new Note(30101, 1428, KeyCodes.NUMPAD5),
                new Note(42515, 2252, KeyCodes.NUMPAD5),
                new Note(54600, 604, KeyCodes.NUMPAD5),
                new Note(75034, 4998, KeyCodes.NUMPAD5),
                new Note(90414, 1373, KeyCodes.NUMPAD5),
                new Note(108486, 6152, KeyCodes.NUMPAD5),
                new Note(120461, 2361, KeyCodes.NUMPAD5),
                new Note(30156, 1428, KeyCodes.NUMPAD6),
                new Note(54600, 5383, KeyCodes.NUMPAD6),
                new Note(68168, 3954, KeyCodes.NUMPAD6),
                new Note(80033, 4229, KeyCodes.NUMPAD6),
                new Note(91403, 1702, KeyCodes.NUMPAD6),
                new Note(106509, 5383, KeyCodes.NUMPAD6),
                new Note(118978, 1538, KeyCodes.NUMPAD6),
                new Note(14171, 3900, KeyCodes.NUMPAD7),
                new Note(23070, 1922, KeyCodes.NUMPAD7),
                new Note(33452, 4669, KeyCodes.NUMPAD7),
                new Note(44822, 2087, KeyCodes.NUMPAD7),
                new Note(66080, 2581, KeyCodes.NUMPAD7),
                new Note(75034, 4943, KeyCodes.NUMPAD7),
                new Note(92557, 2966, KeyCodes.NUMPAD7),
                new Note(105245, 4614, KeyCodes.NUMPAD7),
                new Note(126339, 1647, KeyCodes.NUMPAD7),
                new Note(134633, 2416, KeyCodes.NUMPAD7),
                new Note(54490, 2307, KeyCodes.NUMPAD7),
                new Note(33507, 4669, KeyCodes.NUMPAD8),
                new Note(39714, 2197, KeyCodes.NUMPAD8),
                new Note(55699, 4284, KeyCodes.NUMPAD8),
                new Note(103707, 5218, KeyCodes.NUMPAD8),
                new Note(129634, 933, KeyCodes.NUMPAD8),
                new Note(38176, 439, KeyCodes.NUMPAD9),
                new Note(48503, 659, KeyCodes.NUMPAD9),
                new Note(101565, 4778, KeyCodes.NUMPAD9)));
        notes.sort(null);
        return new Song("./src/media/track1.mp3", "Cinematic Experience", 137325, notes, SongDifficulties.EASY, 0, 0);
    }

    public static Song song2() {
        List<Note> notes = new ArrayList<>(List.of(
                new Note(247, 495, KeyCodes.NUMPAD1),
                new Note(742, 495, KeyCodes.NUMPAD2),
                new Note(1237, 544, KeyCodes.NUMPAD3),
                new Note(1782, 544, KeyCodes.NUMPAD4),
                new Note(2326, 544, KeyCodes.NUMPAD5),
                new Note(2871, 495, KeyCodes.NUMPAD6),
                new Note(3366, 594, KeyCodes.NUMPAD7),
                new Note(3960, 495, KeyCodes.NUMPAD8),
                new Note(4455, 544, KeyCodes.NUMPAD9),
                new Note(5000, 495, KeyCodes.NUMPAD1),
                new Note(5545, 495, KeyCodes.NUMPAD2),
                new Note(6090, 544, KeyCodes.NUMPAD3),
                new Note(6635, 544, KeyCodes.NUMPAD4),
                new Note(7180, 544, KeyCodes.NUMPAD5),
                new Note(7725, 495, KeyCodes.NUMPAD6),
                new Note(8270, 594, KeyCodes.NUMPAD7),
                new Note(8815, 495, KeyCodes.NUMPAD8),
                new Note(9360, 544, KeyCodes.NUMPAD9),
                new Note(9905, 495, KeyCodes.NUMPAD1),
                new Note(10450, 495, KeyCodes.NUMPAD2),
                new Note(10995, 544, KeyCodes.NUMPAD3),
                new Note(11540, 544, KeyCodes.NUMPAD4),
                new Note(12085, 544, KeyCodes.NUMPAD5),
                new Note(12630, 495, KeyCodes.NUMPAD6),
                new Note(13175, 594, KeyCodes.NUMPAD7),
                new Note(13720, 495, KeyCodes.NUMPAD8),
                new Note(14265, 544, KeyCodes.NUMPAD9),
                new Note(14810, 495, KeyCodes.NUMPAD1),
                new Note(15355, 495, KeyCodes.NUMPAD2),
                new Note(15900, 544, KeyCodes.NUMPAD3),
                new Note(16445, 544, KeyCodes.NUMPAD4),
                new Note(16990, 544, KeyCodes.NUMPAD5),
                new Note(17535, 495, KeyCodes.NUMPAD6),
                new Note(18080, 594, KeyCodes.NUMPAD7),
                new Note(18625, 495, KeyCodes.NUMPAD8),
                new Note(19170, 544, KeyCodes.NUMPAD9),
                new Note(19715, 495, KeyCodes.NUMPAD4),
                new Note(20260, 544, KeyCodes.NUMPAD2),
                new Note(20805, 594, KeyCodes.NUMPAD6),
                new Note(21350, 495, KeyCodes.NUMPAD8),
                new Note(21895, 544, KeyCodes.NUMPAD1),
                new Note(22440, 495, KeyCodes.NUMPAD3),
                new Note(22985, 594, KeyCodes.NUMPAD7),
                new Note(23530, 544, KeyCodes.NUMPAD5),
                new Note(24075, 495, KeyCodes.NUMPAD9),
                new Note(24620, 544, KeyCodes.NUMPAD2),
                new Note(25165, 495, KeyCodes.NUMPAD4),
                new Note(25710, 594, KeyCodes.NUMPAD6),
                new Note(26255, 495, KeyCodes.NUMPAD8),
                new Note(26800, 544, KeyCodes.NUMPAD1),
                new Note(27345, 495, KeyCodes.NUMPAD3),
                new Note(27890, 594, KeyCodes.NUMPAD7),
                new Note(28435, 544, KeyCodes.NUMPAD5),
                new Note(28980, 495, KeyCodes.NUMPAD9),
                new Note(29525, 544, KeyCodes.NUMPAD2),
                new Note(30070, 495, KeyCodes.NUMPAD4),
                new Note(30615, 594, KeyCodes.NUMPAD6),
                new Note(31160, 495, KeyCodes.NUMPAD8),
                new Note(31705, 544, KeyCodes.NUMPAD1),
                new Note(32250, 495, KeyCodes.NUMPAD3),
                new Note(32795, 594, KeyCodes.NUMPAD7),
                new Note(33340, 544, KeyCodes.NUMPAD5),
                new Note(33885, 495, KeyCodes.NUMPAD9),
                new Note(34430, 544, KeyCodes.NUMPAD2),
                new Note(34975, 495, KeyCodes.NUMPAD4),
                new Note(35520, 594, KeyCodes.NUMPAD6),
                new Note(36065, 495, KeyCodes.NUMPAD8),
                new Note(36610, 544, KeyCodes.NUMPAD1),
                new Note(37155, 495, KeyCodes.NUMPAD3),
                new Note(37700, 594, KeyCodes.NUMPAD7),
                new Note(38245, 544, KeyCodes.NUMPAD5),
                new Note(38790, 495, KeyCodes.NUMPAD9),
                new Note(39335, 544, KeyCodes.NUMPAD2),
                new Note(39880, 495, KeyCodes.NUMPAD4),
                new Note(40425, 594, KeyCodes.NUMPAD6),
                new Note(40970, 495, KeyCodes.NUMPAD8),
                new Note(41515, 544, KeyCodes.NUMPAD1),
                new Note(42060, 495, KeyCodes.NUMPAD3),
                new Note(42605, 594, KeyCodes.NUMPAD7),
                new Note(43150, 544, KeyCodes.NUMPAD5),
                new Note(43695, 495, KeyCodes.NUMPAD9),
                new Note(44240, 544, KeyCodes.NUMPAD2),
                new Note(44785, 495, KeyCodes.NUMPAD4),
                new Note(45330, 594, KeyCodes.NUMPAD6),
                new Note(45875, 495, KeyCodes.NUMPAD8),
                new Note(46420, 544, KeyCodes.NUMPAD1),
                new Note(46965, 495, KeyCodes.NUMPAD3),
                new Note(47510, 594, KeyCodes.NUMPAD7),
                new Note(48055, 544, KeyCodes.NUMPAD5),
                new Note(48600, 495, KeyCodes.NUMPAD9),
                new Note(49145, 544, KeyCodes.NUMPAD2),
                new Note(49690, 495, KeyCodes.NUMPAD4),
                new Note(50235, 594, KeyCodes.NUMPAD6),
                new Note(50780, 495, KeyCodes.NUMPAD8),
                new Note(51325, 544, KeyCodes.NUMPAD1),
                new Note(51870, 495, KeyCodes.NUMPAD3),
                new Note(52415, 594, KeyCodes.NUMPAD7),
                new Note(52960, 544, KeyCodes.NUMPAD5),
                new Note(53505, 495, KeyCodes.NUMPAD9),
                new Note(54050, 544, KeyCodes.NUMPAD2),
                new Note(54595, 495, KeyCodes.NUMPAD4),
                new Note(55140, 594, KeyCodes.NUMPAD6),
                new Note(55685, 495, KeyCodes.NUMPAD8),
                new Note(56230, 544, KeyCodes.NUMPAD1),
                new Note(56775, 495, KeyCodes.NUMPAD3),
                new Note(57320, 594, KeyCodes.NUMPAD7),
                new Note(57865, 544, KeyCodes.NUMPAD5),
                new Note(58410, 495, KeyCodes.NUMPAD9),
                new Note(58955, 544, KeyCodes.NUMPAD2),
                new Note(59500, 495, KeyCodes.NUMPAD4),
                new Note(60045, 594, KeyCodes.NUMPAD6),
                new Note(60590, 495, KeyCodes.NUMPAD8),
                new Note(61135, 544, KeyCodes.NUMPAD1),
                new Note(61680, 495, KeyCodes.NUMPAD3),
                new Note(62225, 594, KeyCodes.NUMPAD7),
                new Note(62770, 544, KeyCodes.NUMPAD5),
                new Note(63315, 495, KeyCodes.NUMPAD9),
                new Note(63860, 544, KeyCodes.NUMPAD2),
                new Note(64405, 495, KeyCodes.NUMPAD4),
                new Note(64950, 594, KeyCodes.NUMPAD6),
                new Note(65495, 495, KeyCodes.NUMPAD8),
                new Note(66040, 544, KeyCodes.NUMPAD1),
                new Note(66585, 495, KeyCodes.NUMPAD3),
                new Note(67130, 594, KeyCodes.NUMPAD7),
                new Note(67675, 544, KeyCodes.NUMPAD5),
                new Note(68220, 495, KeyCodes.NUMPAD9),
                new Note(68765, 544, KeyCodes.NUMPAD2),
                new Note(69310, 495, KeyCodes.NUMPAD4),
                new Note(69855, 594, KeyCodes.NUMPAD6),
                new Note(70400, 495, KeyCodes.NUMPAD8),
                new Note(70945, 544, KeyCodes.NUMPAD1),
                new Note(71490, 495, KeyCodes.NUMPAD3),
                new Note(72035, 594, KeyCodes.NUMPAD7),
                new Note(72580, 544, KeyCodes.NUMPAD5),
                new Note(73125, 495, KeyCodes.NUMPAD9),
                new Note(73670, 544, KeyCodes.NUMPAD2),
                new Note(74215, 495, KeyCodes.NUMPAD4),
                new Note(74760, 594, KeyCodes.NUMPAD6),
                new Note(75305, 495, KeyCodes.NUMPAD8),
                new Note(75850, 544, KeyCodes.NUMPAD1),
                new Note(76395, 495, KeyCodes.NUMPAD3),
                new Note(76940, 594, KeyCodes.NUMPAD7),
                new Note(77485, 544, KeyCodes.NUMPAD5),
                new Note(78030, 495, KeyCodes.NUMPAD9),
                new Note(78575, 544, KeyCodes.NUMPAD2),
                new Note(79120, 495, KeyCodes.NUMPAD4),
                new Note(79665, 594, KeyCodes.NUMPAD6),
                new Note(80210, 495, KeyCodes.NUMPAD8),
                new Note(80755, 544, KeyCodes.NUMPAD1),
                new Note(81300, 495, KeyCodes.NUMPAD3),
                new Note(81845, 594, KeyCodes.NUMPAD7),
                new Note(82390, 544, KeyCodes.NUMPAD5),
                new Note(82935, 495, KeyCodes.NUMPAD9),
                new Note(83480, 544, KeyCodes.NUMPAD2),
                new Note(84025, 495, KeyCodes.NUMPAD4),
                new Note(84570, 594, KeyCodes.NUMPAD6),
                new Note(85115, 495, KeyCodes.NUMPAD8),
                new Note(85660, 544, KeyCodes.NUMPAD1),
                new Note(86205, 495, KeyCodes.NUMPAD3),
                new Note(86750, 594, KeyCodes.NUMPAD7),
                new Note(87295, 544, KeyCodes.NUMPAD5),
                new Note(87840, 495, KeyCodes.NUMPAD9),
                new Note(88385, 544, KeyCodes.NUMPAD2),
                new Note(88930, 495, KeyCodes.NUMPAD4),
                new Note(89475, 594, KeyCodes.NUMPAD6),
                new Note(90020, 495, KeyCodes.NUMPAD8),
                new Note(90565, 544, KeyCodes.NUMPAD1),
                new Note(91110, 495, KeyCodes.NUMPAD3),
                new Note(91655, 594, KeyCodes.NUMPAD7),
                new Note(92200, 544, KeyCodes.NUMPAD5),
                new Note(92745, 495, KeyCodes.NUMPAD9),
                new Note(93290, 544, KeyCodes.NUMPAD2),
                new Note(93835, 495, KeyCodes.NUMPAD4),
                new Note(94380, 594, KeyCodes.NUMPAD6),
                new Note(94925, 495, KeyCodes.NUMPAD8),
                new Note(95470, 544, KeyCodes.NUMPAD1),
                new Note(96015, 495, KeyCodes.NUMPAD3),
                new Note(96560, 594, KeyCodes.NUMPAD7),
                new Note(97105, 544, KeyCodes.NUMPAD5),
                new Note(97650, 495, KeyCodes.NUMPAD9),
                new Note(98195, 544, KeyCodes.NUMPAD2),
                new Note(98740, 495, KeyCodes.NUMPAD4),
                new Note(99285, 594, KeyCodes.NUMPAD6),
                new Note(99830, 495, KeyCodes.NUMPAD8),
                new Note(100375, 544, KeyCodes.NUMPAD1),
                new Note(100920, 495, KeyCodes.NUMPAD3),
                new Note(101465, 594, KeyCodes.NUMPAD7),
                new Note(102010, 544, KeyCodes.NUMPAD5),
                new Note(102555, 495, KeyCodes.NUMPAD9),
                new Note(103100, 544, KeyCodes.NUMPAD2),
                new Note(103645, 495, KeyCodes.NUMPAD4),
                new Note(104190, 594, KeyCodes.NUMPAD6),
                new Note(104735, 495, KeyCodes.NUMPAD8),
                new Note(105280, 544, KeyCodes.NUMPAD1),
                new Note(105825, 495, KeyCodes.NUMPAD3),
                new Note(106370, 594, KeyCodes.NUMPAD7),
                new Note(106915, 544, KeyCodes.NUMPAD5),
                new Note(107460, 495, KeyCodes.NUMPAD9),
                new Note(108005, 544, KeyCodes.NUMPAD2),
                new Note(108550, 495, KeyCodes.NUMPAD4),
                new Note(109095, 594, KeyCodes.NUMPAD6),
                new Note(109640, 495, KeyCodes.NUMPAD8),
                new Note(110185, 544, KeyCodes.NUMPAD1),
                new Note(110730, 495, KeyCodes.NUMPAD3),
                new Note(111275, 594, KeyCodes.NUMPAD7),
                new Note(111820, 544, KeyCodes.NUMPAD5),
                new Note(112365, 495, KeyCodes.NUMPAD9),
                new Note(112910, 544, KeyCodes.NUMPAD2),
                new Note(113455, 495, KeyCodes.NUMPAD4),
                new Note(114000, 594, KeyCodes.NUMPAD6),
                new Note(114545, 495, KeyCodes.NUMPAD8),
                new Note(115090, 544, KeyCodes.NUMPAD1),
                new Note(115635, 495, KeyCodes.NUMPAD3),
                new Note(116180, 594, KeyCodes.NUMPAD7),
                new Note(116725, 544, KeyCodes.NUMPAD5),
                new Note(117270, 495, KeyCodes.NUMPAD9),
                new Note(117815, 544, KeyCodes.NUMPAD2),
                new Note(118360, 495, KeyCodes.NUMPAD4),
                new Note(118905, 594, KeyCodes.NUMPAD6),
                new Note(119450, 495, KeyCodes.NUMPAD8),
                new Note(119995, 544, KeyCodes.NUMPAD1),
                new Note(120540, 495, KeyCodes.NUMPAD3),
                new Note(121085, 594, KeyCodes.NUMPAD7),
                new Note(121630, 544, KeyCodes.NUMPAD5),
                new Note(122175, 495, KeyCodes.NUMPAD9),
                new Note(122720, 544, KeyCodes.NUMPAD2),
                new Note(123265, 495, KeyCodes.NUMPAD4)));
        notes.sort(null);
        return new Song("./src/media/track2.mp3", "Funny Bubbles", 123768, notes, SongDifficulties.MEDIUM, 1, 0);
    }

    public static Song song3() {
        List<Note> notes = new ArrayList<>(List.of(
                new Note(155931, 1762, KeyCodes.NUMPAD1),
                new Note(162181, 881, KeyCodes.NUMPAD1),
                new Note(170675, 4487, KeyCodes.NUMPAD1),
                new Note(181733, 3605, KeyCodes.NUMPAD1),
                new Note(7051, 480, KeyCodes.NUMPAD1),
                new Note(16987, 1041, KeyCodes.NUMPAD1),
                new Note(29086, 1282, KeyCodes.NUMPAD1),
                new Note(51683, 480, KeyCodes.NUMPAD1),
                new Note(70754, 1282, KeyCodes.NUMPAD1),
                new Note(95113, 1442, KeyCodes.NUMPAD1),
                new Note(103526, 721, KeyCodes.NUMPAD1),
                new Note(132934, 3285, KeyCodes.NUMPAD1),
                new Note(38381, 881, KeyCodes.NUMPAD1),
                new Note(63302, 641, KeyCodes.NUMPAD1),
                new Note(138783, 3044, KeyCodes.NUMPAD2),
                new Note(150482, 1201, KeyCodes.NUMPAD2),
                new Note(156251, 2403, KeyCodes.NUMPAD2),
                new Note(161059, 1762, KeyCodes.NUMPAD2),
                new Note(169633, 2644, KeyCodes.NUMPAD2),
                new Note(182293, 3605, KeyCodes.NUMPAD2),
                new Note(188624, 6009, KeyCodes.NUMPAD2),
                new Note(1041, 961, KeyCodes.NUMPAD2),
                new Note(29167, 1201, KeyCodes.NUMPAD2),
                new Note(41506, 1602, KeyCodes.NUMPAD2),
                new Note(57693, 961, KeyCodes.NUMPAD2),
                new Note(81170, 2323, KeyCodes.NUMPAD2),
                new Note(100562, 2644, KeyCodes.NUMPAD2),
                new Note(115706, 1121, KeyCodes.NUMPAD2),
                new Note(147197, 2003, KeyCodes.NUMPAD3),
                new Note(156492, 1121, KeyCodes.NUMPAD3),
                new Note(159457, 1121, KeyCodes.NUMPAD3),
                new Note(161380, 4887, KeyCodes.NUMPAD3),
                new Note(169072, 4407, KeyCodes.NUMPAD3),
                new Note(178607, 6650, KeyCodes.NUMPAD3),
                new Note(193191, 2804, KeyCodes.NUMPAD3),
                new Note(11939, 1041, KeyCodes.NUMPAD3),
                new Note(29167, 1201, KeyCodes.NUMPAD3),
                new Note(41667, 1522, KeyCodes.NUMPAD3),
                new Note(51763, 641, KeyCodes.NUMPAD3),
                new Note(74199, 1121, KeyCodes.NUMPAD3),
                new Note(95433, 1282, KeyCodes.NUMPAD3),
                new Note(110498, 1121, KeyCodes.NUMPAD3),
                new Note(135258, 3766, KeyCodes.NUMPAD3),
                new Note(136940, 4407, KeyCodes.NUMPAD4),
                new Note(146556, 3525, KeyCodes.NUMPAD4),
                new Note(155691, 1762, KeyCodes.NUMPAD4),
                new Note(163463, 1842, KeyCodes.NUMPAD4),
                new Note(170675, 1682, KeyCodes.NUMPAD4),
                new Note(187342, 1602, KeyCodes.NUMPAD4),
                new Note(192470, 2403, KeyCodes.NUMPAD4),
                new Note(11057, 1842, KeyCodes.NUMPAD4),
                new Note(33333, 2804, KeyCodes.NUMPAD4),
                new Note(43029, 1121, KeyCodes.NUMPAD4),
                new Note(48878, 2083, KeyCodes.NUMPAD4),
                new Note(61058, 1041, KeyCodes.NUMPAD4),
                new Note(85978, 1121, KeyCodes.NUMPAD4),
                new Note(97998, 2564, KeyCodes.NUMPAD4),
                new Note(110418, 1282, KeyCodes.NUMPAD4),
                new Note(129088, 2323, KeyCodes.NUMPAD4),
                new Note(20753, 801, KeyCodes.NUMPAD4),
                new Note(147678, 6009, KeyCodes.NUMPAD5),
                new Note(159216, 3846, KeyCodes.NUMPAD5),
                new Note(171476, 3605, KeyCodes.NUMPAD5),
                new Note(180290, 1682, KeyCodes.NUMPAD5),
                new Note(185499, 3044, KeyCodes.NUMPAD5),
                new Note(190386, 1522, KeyCodes.NUMPAD5),
                new Note(195675, 4086, KeyCodes.NUMPAD5),
                new Note(12980, 1602, KeyCodes.NUMPAD5),
                new Note(25160, 961, KeyCodes.NUMPAD5),
                new Note(34375, 1762, KeyCodes.NUMPAD5),
                new Note(43990, 560, KeyCodes.NUMPAD5),
                new Note(48958, 2083, KeyCodes.NUMPAD5),
                new Note(77084, 1201, KeyCodes.NUMPAD5),
                new Note(93751, 641, KeyCodes.NUMPAD5),
                new Note(98078, 2884, KeyCodes.NUMPAD5),
                new Note(122838, 4246, KeyCodes.NUMPAD5),
                new Note(56491, 1041, KeyCodes.NUMPAD5),
                new Note(144953, 3525, KeyCodes.NUMPAD6),
                new Note(160578, 2804, KeyCodes.NUMPAD6),
                new Note(167950, 2323, KeyCodes.NUMPAD6),
                new Note(175402, 5849, KeyCodes.NUMPAD6),
                new Note(182854, 1522, KeyCodes.NUMPAD6),
                new Note(189104, 3205, KeyCodes.NUMPAD6),
                new Note(195274, 2003, KeyCodes.NUMPAD6),
                new Note(5128, 881, KeyCodes.NUMPAD6),
                new Note(24279, 1923, KeyCodes.NUMPAD6),
                new Note(35016, 1201, KeyCodes.NUMPAD6),
                new Note(49039, 2003, KeyCodes.NUMPAD6),
                new Note(69071, 1282, KeyCodes.NUMPAD6),
                new Note(90706, 1842, KeyCodes.NUMPAD6),
                new Note(113062, 1522, KeyCodes.NUMPAD6),
                new Note(124520, 1842, KeyCodes.NUMPAD6),
                new Note(148399, 6250, KeyCodes.NUMPAD7),
                new Note(160098, 2403, KeyCodes.NUMPAD7),
                new Note(168271, 2884, KeyCodes.NUMPAD7),
                new Note(178447, 3125, KeyCodes.NUMPAD7),
                new Note(185979, 1682, KeyCodes.NUMPAD7),
                new Note(191028, 560, KeyCodes.NUMPAD7),
                new Note(3044, 961, KeyCodes.NUMPAD7),
                new Note(25160, 1201, KeyCodes.NUMPAD7),
                new Note(36538, 1602, KeyCodes.NUMPAD7),
                new Note(66106, 961, KeyCodes.NUMPAD7),
                new Note(93831, 801, KeyCodes.NUMPAD7),
                new Note(106571, 2403, KeyCodes.NUMPAD7),
                new Note(113382, 1282, KeyCodes.NUMPAD7),
                new Note(124761, 1282, KeyCodes.NUMPAD7),
                new Note(133815, 2403, KeyCodes.NUMPAD7),
                new Note(159376, 2403, KeyCodes.NUMPAD8),
                new Note(165466, 1762, KeyCodes.NUMPAD8),
                new Note(178127, 2083, KeyCodes.NUMPAD8),
                new Note(186620, 2564, KeyCodes.NUMPAD8),
                new Note(192069, 1762, KeyCodes.NUMPAD8),
                new Note(14984, 2964, KeyCodes.NUMPAD8),
                new Note(46154, 1121, KeyCodes.NUMPAD8),
                new Note(53446, 801, KeyCodes.NUMPAD8),
                new Note(77004, 1522, KeyCodes.NUMPAD8),
                new Note(90866, 1923, KeyCodes.NUMPAD8),
                new Note(106651, 2484, KeyCodes.NUMPAD8),
                new Note(117549, 3525, KeyCodes.NUMPAD8),
                new Note(135338, 5128, KeyCodes.NUMPAD8),
                new Note(143351, 4487, KeyCodes.NUMPAD9),
                new Note(171236, 3205, KeyCodes.NUMPAD9),
                new Note(178607, 801, KeyCodes.NUMPAD9),
                new Note(180611, 1041, KeyCodes.NUMPAD9),
                new Note(183736, 2083, KeyCodes.NUMPAD9),
                new Note(189185, 1762, KeyCodes.NUMPAD9),
                new Note(9134, 2003, KeyCodes.NUMPAD9),
                new Note(36779, 1602, KeyCodes.NUMPAD9),
                new Note(46394, 961, KeyCodes.NUMPAD9),
                new Note(65145, 881, KeyCodes.NUMPAD9),
                new Note(85257, 2884, KeyCodes.NUMPAD9),
                new Note(97917, 1442, KeyCodes.NUMPAD9),
                new Note(104408, 801, KeyCodes.NUMPAD9),
                new Note(117549, 1602, KeyCodes.NUMPAD9),
                new Note(130049, 2484, KeyCodes.NUMPAD9),
                new Note(39423, 1041, KeyCodes.NUMPAD9),
                new Note(22596, 1923, KeyCodes.NUMPAD9),
                new Note(56651, 1041, KeyCodes.NUMPAD9),
                new Note(72276, 1842, KeyCodes.NUMPAD9)));
        notes.sort(null);
        return new Song("./src/media/track3.mp3", "Three Sheets To The Wind", 200323, notes, SongDifficulties.HARD, 2,
                0);
    }

}
