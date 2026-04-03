package shedar.mods.ic2.nuclearcontrol.renderers;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;

import org.lwjgl.opengl.GL11;

import com.github.bsideup.jabel.Desugar;

import shedar.mods.ic2.nuclearcontrol.IScreenPart;
import shedar.mods.ic2.nuclearcontrol.api.CardState;
import shedar.mods.ic2.nuclearcontrol.api.DisplaySettingHelper;
import shedar.mods.ic2.nuclearcontrol.api.IPanelDataSource;
import shedar.mods.ic2.nuclearcontrol.api.PanelString;
import shedar.mods.ic2.nuclearcontrol.panel.CardWrapperImpl;
import shedar.mods.ic2.nuclearcontrol.panel.Screen;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityAdvancedInfoPanel;
import shedar.mods.ic2.nuclearcontrol.tileentities.TileEntityInfoPanel;
import shedar.mods.ic2.nuclearcontrol.utils.StringUtils;

public class TileEntityInfoPanelRenderer extends TileEntitySpecialRenderer {

    private static final float BLOCK_PIXEL = 1F / 16F;
    private static final float DISPLAY_MARGIN = 2F / 16F;
    private static final float POLYGON_OFFSET = -10F;
    private static final int LINE_SPACING = 2;
    private static final int TEXT_PADDING_X = 4;

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        if (!(tileEntity instanceof TileEntityInfoPanel panel)) return;
        if (!panel.getPowered()) return;

        List<PanelString> data = getCardData(panel.getCards(), panel);
        if (data.isEmpty()) return;

        RenderContext ctx = buildRenderContext(panel, x, y, z);

        GL11.glPushMatrix();
        GL11.glPolygonOffset(POLYGON_OFFSET, POLYGON_OFFSET);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        applyTransforms(ctx, panel);

        renderText(data, panel, ctx);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPopMatrix();
    }

    @Desugar
    private record RenderContext(double x, double y, double z, float displayWidth, float displayHeight, short side,
            double[] deltas) {}

    private RenderContext buildRenderContext(TileEntityInfoPanel panel, double x, double y, double z) {
        short side = (short) Facing.oppositeSide[panel.getFacing()];
        Screen screen = panel.getScreen();

        float displayWidth = 1 - DISPLAY_MARGIN;
        float displayHeight = 1 - DISPLAY_MARGIN;
        double[] deltas = null;

        if (screen != null) {
            x += resolveX(panel, screen, side);
            y += screen.maxY - panel.yCoord;
            z += resolveZ(panel, screen, side);

            displayWidth += resolveDisplayWidth(screen, side);
            displayHeight += resolveDisplayHeight(screen, side);
        }

        if (panel instanceof TileEntityAdvancedInfoPanel adv && screen != null) {
            deltas = adv.screenModelInfo.getDeltas();
        }

        return new RenderContext(x, y, z, displayWidth, displayHeight, side, deltas);
    }

    private double resolveX(TileEntityInfoPanel panel, Screen screen, short side) {
        return (side == 0 || side == 2 || side == 4) ? screen.minX - panel.xCoord : screen.maxX - panel.xCoord;
    }

    private double resolveZ(TileEntityInfoPanel panel, Screen screen, short side) {
        return (side == 0 || side == 1 || side == 2 || side == 3 || side == 5) ? screen.minZ - panel.zCoord
                : screen.maxZ - panel.zCoord;
    }

    private float resolveDisplayWidth(Screen screen, short side) {
        return (side == 4 || side == 5) ? screen.maxZ - screen.minZ : screen.maxX - screen.minX;
    }

    private float resolveDisplayHeight(Screen screen, short side) {
        return (side == 0 || side == 1) ? screen.maxZ - screen.minZ : screen.maxY - screen.minY;
    }

    private void applyTransforms(RenderContext ctx, TileEntityInfoPanel panel) {
        GL11.glTranslatef((float) ctx.x(), (float) ctx.y(), (float) ctx.z());
        applySideRotation(ctx);

        float thickness = computeThickness(panel, ctx.deltas());
        GL11.glTranslatef(BLOCK_PIXEL + ctx.displayWidth() / 2, thickness, BLOCK_PIXEL + ctx.displayHeight() / 2);
        GL11.glRotatef(-90, 1, 0, 0);
        GL11.glColor4f(1F, 1F, 1F, 1F);
        applyPanelRotation(panel);
        applyTiltAngles(ctx);
        GL11.glRotatef(panel.getTextRotation() * 90F, 0, 0, 1);
    }

    private void applySideRotation(RenderContext ctx) {
        switch (ctx.side()) {
            case 1 -> {
                GL11.glTranslatef(1, 1, 0);
                GL11.glRotatef(180, 1, 0, 0);
                GL11.glRotatef(180, 0, 1, 0);
            }
            case 2 -> {
                GL11.glTranslatef(0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
            }
            case 3 -> {
                GL11.glTranslatef(1, 1, 1);
                GL11.glRotatef(180, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
            }
            case 4 -> {
                GL11.glTranslatef(0, 1, 1);
                GL11.glRotatef(90, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
            }
            case 5 -> {
                GL11.glTranslatef(1, 1, 0);
                GL11.glRotatef(-90, 0, 1, 0);
                GL11.glRotatef(90, 1, 0, 0);
            }
            default -> {} // case 0 has no transform
        }
    }

    private void applyPanelRotation(TileEntityInfoPanel panel) {
        switch (panel.rotation) {
            case 1 -> GL11.glRotatef(-90, 0, 0, 1);
            case 2 -> GL11.glRotatef(90, 0, 0, 1);
            case 3 -> GL11.glRotatef(180, 0, 0, 1);
            default -> {} // case 0 has no transform
        }
    }

    private float computeThickness(TileEntityInfoPanel panel, double[] deltas) {
        if (panel instanceof TileEntityAdvancedInfoPanel adv && deltas != null) {
            return (float) (adv.thickness / 16F - (deltas[0] + deltas[1] + deltas[2] + deltas[3]) / 4);
        }
        return 1F;
    }

    private void applyTiltAngles(RenderContext ctx) {
        double[] d = ctx.deltas();
        if (d == null) return;

        double angleHor, angleVert;
        float w = ctx.displayWidth() + DISPLAY_MARGIN;
        float h = ctx.displayHeight() + DISPLAY_MARGIN;

        if (d[0] == 0) {
            angleHor = toDeg(d[1], w);
            angleVert = -toDeg(d[2], h);
        } else if (d[1] == 0) {
            angleHor = -toDeg(d[0], w);
            angleVert = -toDeg(d[3], h);
        } else if (d[2] == 0) {
            angleHor = toDeg(d[3], w);
            angleVert = toDeg(d[0], h);
        } else {
            angleHor = -toDeg(d[2], w);
            angleVert = toDeg(d[1], h);
        }

        GL11.glRotatef((float) -angleVert, 1, 0, 0);
        GL11.glRotatef((float) angleHor, 0, 1, 0);
    }

    private double toDeg(double delta, float span) {
        return 180 / Math.PI * Math.atan(delta / span);
    }

    private void renderText(List<PanelString> data, TileEntityInfoPanel panel, RenderContext ctx) {
        FontRenderer fr = this.func_147498_b();

        float displayWidth = ctx.displayWidth();
        float displayHeight = ctx.displayHeight();

        // Swap if rotation 90°/270°
        if (panel.getTextRotation() == 1 || panel.getTextRotation() == 3) {
            float tmp = displayWidth;
            displayWidth = displayHeight;
            displayHeight = tmp;
        }

        int maxWidth = computeMaxWidth(fr, data);
        int lineHeight = fr.FONT_HEIGHT + LINE_SPACING;
        int totalHeight = lineHeight * data.size();

        float scaleX = displayWidth / maxWidth;
        float scaleY = displayHeight / totalHeight;
        float scale = Math.min(scaleX, scaleY);

        GL11.glScalef(scale, -scale, scale);
        GL11.glDisable(GL11.GL_LIGHTING);

        int realWidth = (int) Math.floor(displayWidth / scale);
        int realHeight = (int) Math.floor(displayHeight / scale);
        int offsetX = (scaleX < scaleY) ? 2 : (realWidth - maxWidth) / 2 + 2;
        int offsetY = (scaleX < scaleY) ? (realHeight - totalHeight) / 2 : 0;

        int halfWidth = realWidth / 2;
        int fallbackColor = panel.getColorTextHex();

        for (int row = 0; row < data.size(); row++) {
            PanelString ps = data.get(row);
            int yPos = offsetY - realHeight / 2 + row * lineHeight;

            renderPanelString(fr, ps.textLeft, offsetX - halfWidth, 1 + yPos, ps.colorLeft, fallbackColor);
            renderPanelString(
                    fr,
                    ps.textCenter,
                    -fr.getStringWidth(ps.textCenter) / 2,
                    yPos,
                    ps.colorCenter,
                    fallbackColor);
            renderPanelString(
                    fr,
                    ps.textRight,
                    halfWidth - fr.getStringWidth(ps.textRight),
                    yPos,
                    ps.colorRight,
                    fallbackColor);
        }
    }

    private int computeMaxWidth(FontRenderer fr, List<PanelString> data) {
        int max = 1;
        for (PanelString ps : data) {
            max = Math.max(fr.getStringWidth(ps.fullDisplayString()), max);
        }
        return max + TEXT_PADDING_X;
    }

    private List<PanelString> getCardData(List<ItemStack> cards, TileEntityInfoPanel panel) {
        List<PanelString> result = new ArrayList<>();
        for (ItemStack card : cards) {
            if (card == null || !(card.getItem() instanceof IPanelDataSource)) continue;

            CardWrapperImpl helper = new CardWrapperImpl(card);
            CardState state = helper.getState();
            DisplaySettingHelper settings = panel.getNewDisplaySettingsByCard(card);

            List<PanelString> data = (state != CardState.OK && state != CardState.CUSTOM_ERROR)
                    ? StringUtils.getStateMessage(state)
                    : resolveCardData(panel, settings, card, helper);

            if (data != null) result.addAll(data);
        }
        return result;
    }

    private List<PanelString> resolveCardData(TileEntityInfoPanel panel, DisplaySettingHelper settings, ItemStack card,
            CardWrapperImpl helper) {
        if (panel instanceof TileEntityAdvancedInfoPanel adv) {
            return adv.getSortedCardData(settings, card, helper);
        }
        return panel.getCardData(settings, card, helper).getCardDataWithTitle();
    }

    private void renderPanelString(FontRenderer fr, String text, int x, int y, int color, int fallback) {
        if (text == null) return;
        fr.drawString(text, x, y, color != 0 ? color : fallback);
    }
}
