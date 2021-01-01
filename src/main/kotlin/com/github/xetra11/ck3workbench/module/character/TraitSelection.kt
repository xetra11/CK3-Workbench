package com.github.xetra11.ck3workbench.module.character

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

/**
 * Holds the trait selection state and provides the trait button composable
 */
class TraitSelection {
    private val traitIconPath = "icons/trait_icons"

    interface Trait {
        val code: String
        val label: String
    }

    interface LeveledTrait : Trait

    enum class DescendantTrait(override val code: String, override val label: String) : LeveledTrait {
        SAOSHYANT("saoshyant", "Saoshyant"),
        SAYYID("sayyid", "Sayyid"),
        PARAGON("paragon", "Paragon"),
        SAVIOR("paragon", "The Savior"),
    }

    enum class DynastyTrait(override val code: String, override val label: String) : LeveledTrait {
        BASTARD("bastard", "Bastard"),
        BASTARD_FOUNDER("bastard_founder", "Bastard Founder"),
        BORN_PURPLE("born_in_the_purple", "Born in the Purple"),
        CHILD_CONCUBINE("child_of_concubine", "Child of Concubine"),
        CHILD_CONSORT("child_of_concubine", "Child of Consort"),
        DENOUNCED("denounced", "Denounced"),
        DISINHERITED("disinherited", "Disinherited"),
        DISPUTED("disputed_heritage", "Disputed Heritage"),
        LEGITIMIZED_BASTARD("legitimized_bastard", "Legitimized Bastard Heritage"),
        REINCARNATION("reincarnation", "Reincarnation"),
        TWIN("twin", "Twin"),
        WILD_OAT("wild_oat", "Wild Oat"),
    }

    enum class DiseaseTrait(override val code: String, override val label: String) : LeveledTrait {
        Ill("ill", "Ill"),
        PLAGUE("bubonic_plague", "Plague"),
        CANCER("cancer", "Cancer"),
        CONSUMPTION("consumption", "Consumption"),
        GREAT_POX("great_pox", "Great Pox"),
        GOUT("gout_ridden", "Gout Ridden"),
        LEPER("leper", "Leper"),
        LOVERS_POX("lovers_pox", "Lover's Pox"),
        PNEUMONIA("pneumonic", "Pneumonia"),
        SMALLPOX("smallpox", "Smallpox"),
        TYPHUS("typhus", "Typhus"),
    }

    enum class HealthTrait(override val code: String, override val label: String) : LeveledTrait {
        WOUNDED("wounded_1", "Wounded"),
        INJURED("wounded_2", "Severely Injured"),
        MAULED("wounded_3", "Brutally Mauled"),
        SICKLY("sickly", "Sickly"),
        IMPOTENT("impotent", "Impotent"),
        INFIRM("infirm", "Infirm"),
        INCAPABLE("incapable", "Incapable"),
        INBRED("inbred", "Inbred"),
        MAIMED("maimed", "Maimed"),
        BLIND("blind", "Blind"),
    }

    enum class ChildhoodTrait(override val code: String, override val label: String) : LeveledTrait {
        BOSSY("bossy", "Bossy"),
        CHARMING("charming", "Charming"),
        CURIOUS("curious", "Curious"),
        PENSIVE("pensive", "Pensive"),
        ROWDY("rowdy", "Rowdy"),
    }

    enum class CopingTrait(override val code: String, override val label: String) : LeveledTrait {
        DRUNKARD("drunkard", "Drunkard"),
        FLAGELLANT("flagellant", "Flagellant"),
        COMFORT_EATER("comfort_eater", "Comfort Eater"),
        CONTRITE("contrite", "Contrite"),
        IMPROVIDENT("improvident", "Improvident"),
        INAPPETETIC("inappetetic", "Inappetetic"),
        RECLUSIVE("reclusive", "Reclusive"),
        IRRITABLE("irritable", "Irritable"),
        RAKISH("rakish", "Rakish"),
        HASHISHIYAH("hashishiyah", "Hashishiyah"),
        PROFLIGATE("profligate", "Profligate"),
        CONFIDER("confider", "Confider"),
        JOURNALLER("journaller", "Journaller"),
        ATHLETIC("athletic", "Athletic"),
    }

    enum class CriminalTrait(override val code: String, override val label: String) : LeveledTrait {
        ADULTERER("adulterer", "Adulterer"),
        FORNICATOR("fornicator", "Fornicator"),
        DEVIANT("deviant", "Deviant"),
        DYNASTIC_KINSLAYER("kinslayer_1", "Dynastic Kinslayer"),
        FAMILY_KINSLAYER("kinslayer_2", "Family Kinslayer"),
        KINSLAYER("kinslayer_3", "Kinslayer"),
        INCESTUOUS("incestuous", "Incestuous"),
        SODOMITE("sodomite", "Sodomite"),
        WITCH("witch", "Witch"),
        CANNIBAL("cannibal", "Cannibal"),
        EXCOMMUNICATED("excommunicated", "Excommunicated"),
    }

    enum class CommanderTrait(override val code: String, override val label: String) : LeveledTrait {
        AGGRESSIVE("aggressive_attacker", "Aggressive Attacker"),
        FLEXIBLE("flexible_leader", "Flexible Leader"),
        FORDER("forder", "Forder"),
        HOLY_WARRIOR("holy_warrior", "Holy Warrior"),
        LOGISTICIAN("logistician", "Logistician"),
        MILITARY_ENGINEER("military_engineer", "Military Engineer"),
        ORGANIZER("organizer", "Organizer"),
        REAVER("reaver", "Reaver"),
        DEFENDER("unyielding_defender", "Unyielding Defender"),
        CAUTIOUS_LEADER("cautious_leader", "Cautious Leader"),
        RECKLESS("reckless", "Reckless"),
        FOREST_FIGHTER("forest_fighter", "Forest Fighter"),
        OPEN_TERRAIN("open_terrain_expert", "Open Terrain Expert"),
        ROUGH_TERRAIN("rough_terrain_expert", "Rough Terrain Expert"),
        DESERT_WARRIOR("desert_warrior", "Desert Warrior"),
        JUNGLE_STALKER("jungle_stalker", "Jungle Stalker"),
    }

    enum class LeveledLifestyleTrait(override val code: String, override val label: String) : LeveledTrait {
        BLADEMASTER("blademaster", "Blademaster"),
        HUNTER("hunter", "Hunter"),
        MYSTIC("mystic", "Mystic"),
        REVELER("reveler", "Reveler"),
        PHYSICIAN("physician", "Physician"),
    }

    enum class LifestyleTrait(override val code: String, override val label: String) : Trait {
        AUGUST("august", "August"),
        DIPLOMAT("diplomat", "Diplomat"),
        FAMILY_FIRST("family_first", "Family First"),
        GALLANT("gallant", "Gallant"),
        OVERSEER("overseer", "Overseer"),
        STRATEGIST("strategist", "Strategist"),
        ADMINISTRATOR("administrator", "Administrator"),
        ARCHITECT("architect", "Architect"),
        AVARICIOUS("avaricious", "Aravicious"),
        SCHEMER("schemer", "Schemer"),
        SEDUCER("seducer", "Seducer"),
        TORTURER("torturer", "Torturer"),
        SCHOLAR("scholar", "Scholar"),
        THEOLOGIAN("theologian", "Theologian"),
        WHOLE_OF_BODY("whole_of_body", "Whole of Body"),
        CELIBATE("celibate", "Celibate"),
        HERBALIST("herbalist", "Herbalist"),
    }

    enum class PhysicalTrait(override val code: String, override val label: String) : Trait {
        SHREWD("shrewd", "Shrewd"),
        STRONG("strong", "Strong"),
        SCARRED("scarred", "Scarred"),
        ONE_EYED("one_eyed", "One-Eyed"),
        ONE_LEGGED("one_legged", "One-Legged"),
        DISFIGURED("disfigured", "Disfigured"),
        WEAK("weak", "Weak"),
        DULL("dull", "Dull"),
        EUNUCH("eunuch", "Eunuch")
    }

    enum class LeveledCongenitalTrait(override val code: String, override val label: String) : LeveledTrait {
        BEAUTY("beauty", "Beauty"),
        INTELLECT("intellect", "Intellect"),
        PHYSIQUE("physique", "Physique"),
    }

    enum class CongenitalTrait(override val code: String, override val label: String) : Trait {
        MELANCHOLIC("depressed", "Melancholic"),
        LUNATIC("lunatic", "Lunatic"),
        POSSESSED("possessed", "Possessed"),
        FECUND("fecund", "Fecund"),
        ALBINO("albino", "Albino"),
        LISPING("lisping", "Lisping"),
        STUTTERING("stuttering", "Stuttering"),
        PURE_BLOODED("pure_blooded", "Pure-blooded"),
        GIANT("giant", "Giant"),
        SCALY("scaly", "Scaly"),
        CLUB_FOOTED("clubfooted", "Club-footed"),
        DWARD("dwarf", "Dwarf"),
        HUNCHBACKED("hunchbacked", "Hunchbacked"),
        BARREN("infertile", "Infertile"),
        WHEEZING("wheezing", "Wheezing"),
        SPINDLY("spindly", "Spindly"),
        BLEEDER("bleeder", "Bleeder"),
    }

    enum class EducationalTrait(override val code: String, override val label: String) : LeveledTrait {
        DIPLOMACY("diplomacy", "Naive Appeaser"),
        INTRIGUE("intrigue", "Amateurish Plotter"),
        MARTIAL("martial", "Misguided Warrior"),
        STEWARDSHIP("stewardship", "Indulgent Wrastel"),
        PROWESS("martial_prowess", "Bumbling Squire"),
    }

    enum class PersonalityTrait(override val code: String, override val label: String) : Trait {
        BRAVE("brave", "Brave"),
        CRAVEN("craven", "Craven"),
        CALM("calm", "Calm"),
        WRATHFUL("wrathful", "Wrathful"),
        CHASTE("chaste", "Chaste"),
        LUSTFUL("lustful", "Lustful"),
        CONTENT("content", "Content"),
        AMBITIOUS("ambitious", "Ambitious"),
        DILIGENT("diligent", "Diligent"),
        LAZY("lazy", "Lazy"),
        FICKLE("fickle", "Fickle"),
        STUBBORN("stubborn", "Stubborn"),
        FORGIVING("forgiving", "Forgiving"),
        VENGEFUL("vengeful", "Vengeful"),
        GENEROUS("generous", "Generous"),
        GREEDY("greedy", "Greedy"),
        GREGARIOUS("gregarious", "Gregarious"),
        SHY("shy", "Shy"),
        HONEST("honest", "Honest"),
        DECEITFUL("deceitful", "Deceitful"),
        HUMBLE("humble", "Humble"),
        ARROGANT("arrogant", "Arrogant"),
        JUST("just", "Just"),
        ARBITRARY("arbitrary", "Arbitrary"),
        PATIENT("patient", "Patient"),
        IMPATIENT("impatient", "Impatient"),
        TEMPERATE("temperate", "Temperate"),
        GLUTTONOUS("gluttonous", "Gluttonous"),
        TRUSTING("trusting", "Trusting"),
        PARANOID("paranoid", "Paranoid"),
        ZEALOUS("zealous", "Zealous"),
        CYNICAL("cynical", "Cynical"),
        COMPASSIONATE("compassionate", "Compassionate"),
        CALLOUS("callous", "Callous"),
        SADISTIC("sadistic", "Sadistic"),
    }

    @Composable
    fun DescendantTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<DescendantTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun DynastyTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<DynastyTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun DiseaseTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<DiseaseTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun HealthTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<HealthTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun ChildhoodTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<ChildhoodTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun CopingTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<CopingTrait>().toList().chunked(5)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun CriminalTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<CriminalTrait>().toList().chunked(4)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun CommanderTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<CommanderTrait>().toList().chunked(6)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun LifestyleTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<LifestyleTrait>().toList().chunked(6)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun PersonalityTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<PersonalityTrait>().toList().chunked(6)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun EducationalTraits(
        selectionState: SnapshotStateMap<LeveledTrait, Int>
    ) {
        Row {
            enumValues<EducationalTrait>().forEach { trait ->
                TraitIcon(trait, selectionState, 4)
            }
        }
    }

    @Composable
    fun LeveledLifestyleTraits(selectionState: SnapshotStateMap<LeveledTrait, Int>) {
        Row {
            enumValues<LeveledLifestyleTrait>().forEach { trait ->
                TraitIcon(trait, selectionState, 3)
            }
        }
    }

    @Composable
    fun LeveledCongenitalTraits(selectionState: SnapshotStateMap<LeveledTrait, Int>) {
        Row {
            enumValues<LeveledCongenitalTrait>().forEach { trait ->
                TraitIcon(trait, selectionState, 6)
            }
        }
    }

    @Composable
    fun PhysicalTraits(selectionState: SnapshotStateMap<Trait, Boolean>) {
        val chunks = enumValues<PhysicalTrait>().toList().chunked(3)
        chunks.forEach { chunk ->
            Row {
                chunk.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    fun CongenitalTraits(
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        val chunks = enumValues<CongenitalTrait>().toList().chunked(6)
        chunks.forEach {
            Row {
                it.forEach {
                    TraitIcon(it, selectionState)
                }
            }
        }
    }

    @Composable
    private fun TraitIcon(
        leveledTrait: LeveledTrait,
        selectionState: SnapshotStateMap<LeveledTrait, Int>,
        maxLevel: Int = 4
    ) {
        var level by remember { mutableStateOf(0) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(0.2F)) }

        Box(
            Modifier.clickable(
                onClick = {
                    level = levelup(level, maxLevel)
                    selectionState[leveledTrait] = level
                    selectionModifier = if (level == 0) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                }
            ),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                modifier = selectionModifier.size(70.dp, 70.dp),
                bitmap = traitImage(leveledTrait, level)
            )
        }
    }

    private fun levelup(level: Int, maxLevel: Int): Int {
        return if (level < maxLevel) level.plus(1) else 0
    }

    @Composable
    private fun TraitIcon(
        trait: Trait,
        selectionState: SnapshotStateMap<Trait, Boolean>
    ) {
        var isSelected by remember { mutableStateOf(false) }
        var selectionModifier by remember { mutableStateOf(Modifier.alpha(0.2F)) }

        Column(
            modifier = Modifier.size(60.dp, 75.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                Modifier.clickable(
                    onClick = {
                        isSelected = !isSelected
                        selectionState[trait] = isSelected
                        selectionModifier = if (!isSelected) Modifier.alpha(0.2F) else Modifier.alpha(1F)
                    }
                ),
                contentAlignment = Alignment.Center,
            ) {
                TraitIconImage(selectionModifier, trait)
            }
            if (isSelected) TraitLabel(trait)
        }
    }

    @Composable
    private fun TraitIconImage(
        selectionModifier: Modifier,
        trait: Trait
    ) {
        Image(
            modifier = selectionModifier,
            bitmap = traitImage(trait)
        )
    }

    @Composable
    private fun TraitLabel(trait: Trait) {
        Text(
            fontSize = TextUnit.Em(0.7),
            text = trait.label
        )
    }

    private fun traitImage(trait: Trait): ImageBitmap {
        return imageFromResource(iconPath(trait.code))
    }

    private fun traitImage(leveledTrait: LeveledTrait, level: Int): ImageBitmap {
        val theLevel = if (level == 0) 1 else level
        return imageFromResource(leveledIconPath(leveledTrait.code, theLevel))
    }

    private fun iconPath(traitCode: String): String {
        return "$traitIconPath/trait_$traitCode.png"
    }

    private fun leveledIconPath(traitCode: String, level: Int): String {
        return "$traitIconPath/trait_${traitCode}_$level.png"
    }
}
