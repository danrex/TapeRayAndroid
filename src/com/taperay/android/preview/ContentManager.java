package com.taperay.android.preview;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ContentManager {
	static final String tag = "ContentManager";

	private ArrayList<Category> categories;
	private ArrayList<MaterialColor> materialColors;
	private Artwork currentArtwork;
	private ArrayList<Artwork> currentArtworks;
	private String currentTitle;
	private MaterialColor currentColor;

	private void retrieveCategories() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient("categories");
		Element root = restClient.index();
		NodeList items = root.getElementsByTagName("category");

		for (int i = 0; i < items.getLength(); i++) {
			Category c = new Category(items.item(i));
			categories.add(c);
		}
	}

	private void retrieveMaterialColors() throws ClientProtocolException, IOException {
		RestClient restClient = new RestClient("material_colors");
		Element root = restClient.index();
		NodeList items = root.getElementsByTagName("material_color");

		for (int i = 0; i < items.getLength(); i++) {
			MaterialColor mc = new MaterialColor(items.item(i));
			materialColors.add(mc);
		}

		currentColor = materialColors.get(0);
	}

	public List<MaterialColor> getMaterialColors() {
		return materialColors;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public Artwork getCurrentArtwork() {
		return currentArtwork;
	}

	public void loadData() throws ClientProtocolException, IOException {
		retrieveCategories();
		retrieveMaterialColors();		
	}

	public String[] getCategoryTitles() {
		String[] list = new String[categories.size()];
		int pos = 0;

		for(Iterator<Category> i = categories.iterator(); i.hasNext();) {
			Category c = (Category) i.next();
			list[pos++] = c.getTitle();
		}

		return list;
	}

	public String[] getArtworkTitles() {
		String[] list = new String[currentArtworks.size()];
		int pos = 0;

		for(Iterator<Artwork> i = currentArtworks.iterator(); i.hasNext();) {
			Artwork a = (Artwork) i.next();
			list[pos++] = a.getTitle();
		}

		return list;
	}

	public String[] getColorTitles() {
		String[] list = new String[materialColors.size()];
		int pos = 0;

		for(Iterator<MaterialColor> i = materialColors.iterator(); i.hasNext();) {
			MaterialColor mc = (MaterialColor) i.next();
			list[pos++] = mc.getTitle();
		}

		return list;
	}

	public void selectCategory(int index) throws ClientProtocolException, IOException {
		Category c = categories.get(index);
		currentArtworks = c.getArtworks();
		currentArtwork = null;
		currentTitle = "TapeRay > " + c.getTitle();
	}

	public void selectArtwork(int index) {
		currentArtwork = currentArtworks.get(index);
	}

	public void selectMaterialColor(int index) {
		currentColor = materialColors.get(index);
	}

	public String getCurrentTitle() {
		return currentTitle;
	}

	ContentManager() {
		categories = new ArrayList<Category>();
		materialColors = new ArrayList<MaterialColor>();
		currentArtwork = null;
	}

	public MaterialColor getCurrentColor() {
		return currentColor;
	}
}
