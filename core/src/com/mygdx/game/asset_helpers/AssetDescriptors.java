package com.mygdx.game.asset_helpers;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Jay Nguyen on 4/15/2017.
 */

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> FONT = new AssetDescriptor<BitmapFont>(AssetPaths.FONT, BitmapFont.class);
    public static final AssetDescriptor<TextureAtlas> GAME_PLAY = new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);
    public static final AssetDescriptor<Skin> SKIN = new AssetDescriptor<Skin>(AssetPaths.SKIN, Skin.class);
    public static final AssetDescriptor<Sound> COIN_SOUND = new AssetDescriptor<Sound>(AssetPaths.COIN_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> JUMP_SOUND = new AssetDescriptor<Sound>(AssetPaths.JUMP_SOUND, Sound.class);
    public static final AssetDescriptor<Sound> LOSE_SOUND = new AssetDescriptor<Sound>(AssetPaths.LOSE_SOUND, Sound.class);

    private AssetDescriptors() {
    }
}
