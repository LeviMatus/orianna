package com.merakianalytics.orianna.datapipeline.transformers.dtodata;

import java.util.ArrayList;
import java.util.List;

import com.merakianalytics.datapipelines.PipelineContext;
import com.merakianalytics.datapipelines.transformers.AbstractDataTransformer;
import com.merakianalytics.datapipelines.transformers.Transform;
import com.merakianalytics.orianna.types.data.league.League;
import com.merakianalytics.orianna.types.data.league.LeagueEntry;
import com.merakianalytics.orianna.types.data.league.LeaguePosition;
import com.merakianalytics.orianna.types.data.league.LeaguePositions;
import com.merakianalytics.orianna.types.data.league.PositionalQueues;
import com.merakianalytics.orianna.types.data.league.Series;
import com.merakianalytics.orianna.types.dto.league.LeagueItem;
import com.merakianalytics.orianna.types.dto.league.LeagueList;
import com.merakianalytics.orianna.types.dto.league.MiniSeries;
import com.merakianalytics.orianna.types.dto.league.PositionalQueuesList;
import com.merakianalytics.orianna.types.dto.league.SummonerPositions;

public class LeagueTransformer extends AbstractDataTransformer {
    @Transform(from = com.merakianalytics.orianna.types.dto.league.LeaguePosition.class, to = LeaguePosition.class)
    public LeaguePosition transform(final com.merakianalytics.orianna.types.dto.league.LeaguePosition item, final PipelineContext context) {
        final LeaguePosition position = new LeaguePosition();
        position.setLeagueId(item.getLeagueId());
        position.setDivision(item.getRank());
        position.setFreshBlood(item.isFreshBlood());
        position.setInactive(item.isInactive());
        position.setLeaguePoints(item.getLeaguePoints());
        position.setLosses(item.getLosses());
        position.setName(item.getLeagueName());
        position.setOnHotStreak(item.isHotStreak());
        position.setPlatform(item.getPlatform());
        if(item.getMiniSeries() != null) {
            position.setPromos(transform(item.getMiniSeries(), context));
        }
        position.setQueue(item.getQueueType());
        position.setSummonerId(item.getSummonerId());
        position.setSummonerName(item.getSummonerName());
        position.setTier(item.getTier());
        position.setVeteran(item.isVeteran());
        position.setWins(item.getWins());
        position.setPosition(item.getPosition());
        return position;
    }

    @Transform(from = League.class, to = LeagueList.class)
    public LeagueList transform(final League item, final PipelineContext context) {
        final LeagueList list = new LeagueList();
        final List<LeagueItem> entries = new ArrayList<>(item.size());
        for(final LeagueEntry entry : item) {
            entries.add(transform(entry, context));
        }
        list.setEntries(entries);
        list.setLeagueId(item.getId());
        list.setName(item.getName());
        list.setPlatform(item.getPlatform());
        list.setQueue(item.getQueue().toString());
        list.setTier(item.getTier().toString());
        return list;
    }

    @Transform(from = LeagueEntry.class, to = LeagueItem.class)
    public LeagueItem transform(final LeagueEntry item, final PipelineContext context) {
        final LeagueItem entry = new LeagueItem();
        entry.setRank(item.getDivision().toString());
        entry.setFreshBlood(item.isFreshBlood());
        entry.setInactive(item.isInactive());
        entry.setLeaguePoints(item.getLeaguePoints());
        entry.setLosses(item.getLosses());
        entry.setHotStreak(item.isOnHotStreak());
        if(item.getPromos() != null) {
            entry.setMiniSeries(transform(item.getPromos(), context));
        }
        entry.setSummonerId(item.getSummonerId());
        entry.setSummonerName(item.getSummonerName());
        entry.setVeteran(item.isVeteran());
        entry.setWins(item.getWins());
        return entry;
    }

    @Transform(from = LeagueItem.class, to = LeagueEntry.class)
    public LeagueEntry transform(final LeagueItem item, final PipelineContext context) {
        final LeagueEntry entry = new LeagueEntry();
        entry.setPlatform((String)context.get("platform"));
        entry.setDivision(item.getRank());
        entry.setFreshBlood(item.isFreshBlood());
        entry.setInactive(item.isInactive());
        entry.setLeaguePoints(item.getLeaguePoints());
        entry.setLosses(item.getLosses());
        entry.setOnHotStreak(item.isHotStreak());
        if(item.getMiniSeries() != null) {
            entry.setPromos(transform(item.getMiniSeries(), context));
        }
        entry.setSummonerId(item.getSummonerId());
        entry.setSummonerName(item.getSummonerName());
        entry.setVeteran(item.isVeteran());
        entry.setWins(item.getWins());
        return entry;
    }

    @Transform(from = LeagueList.class, to = League.class)
    public League transform(final LeagueList item, final PipelineContext context) {
        final Object previous = context.put("platform", item.getPlatform());
        final League league = new League(item.getEntries().size());
        for(final LeagueItem entry : item.getEntries()) {
            league.add(transform(entry, context));
        }
        league.setId(item.getLeagueId());
        league.setName(item.getName());
        league.setPlatform(item.getPlatform());
        league.setQueue(item.getQueue());
        league.setTier(item.getTier());
        context.put("platform", previous);
        return league;
    }

    @Transform(from = LeaguePosition.class, to = com.merakianalytics.orianna.types.dto.league.LeaguePosition.class)
    public com.merakianalytics.orianna.types.dto.league.LeaguePosition transform(final LeaguePosition item, final PipelineContext context) {
        final com.merakianalytics.orianna.types.dto.league.LeaguePosition position = new com.merakianalytics.orianna.types.dto.league.LeaguePosition();
        position.setLeagueId(item.getLeagueId());
        position.setRank(item.getDivision().toString());
        position.setFreshBlood(item.isFreshBlood());
        position.setInactive(item.isInactive());
        position.setLeaguePoints(item.getLeaguePoints());
        position.setLosses(item.getLosses());
        position.setLeagueName(item.getName());
        position.setHotStreak(item.isOnHotStreak());
        position.setPlatform(item.getPlatform());
        if(item.getPromos() != null) {
            position.setMiniSeries(transform(item.getPromos(), context));
        }
        position.setQueueType(item.getQueue().toString());
        position.setSummonerId(item.getSummonerId());
        position.setSummonerName(item.getSummonerName());
        position.setTier(item.getTier().toString());
        position.setVeteran(item.isVeteran());
        position.setWins(item.getWins());
        position.setPosition(item.getPosition());
        return position;
    }

    @Transform(from = LeaguePositions.class, to = SummonerPositions.class)
    public SummonerPositions transform(final LeaguePositions item, final PipelineContext context) {
        final SummonerPositions positions = new SummonerPositions();
        for(final LeaguePosition position : item) {
            positions.add(transform(position, context));
        }
        positions.setPlatform(item.getPlatform());
        positions.setSummonerId(item.getSummonerId());
        return positions;
    }

    @Transform(from = MiniSeries.class, to = Series.class)
    public Series transform(final MiniSeries item, final PipelineContext context) {
        final Series series = new Series();
        series.setLosses(item.getLosses());
        series.setProgress(item.getProgress());
        series.setWinsRequired(item.getTarget());
        series.setWins(item.getWins());
        return series;
    }

    @Transform(from = PositionalQueues.class, to = PositionalQueuesList.class)
    public PositionalQueuesList transform(final PositionalQueues item, final PipelineContext context) {
        final PositionalQueuesList list = new PositionalQueuesList(item.size());
        list.addAll(item);
        list.setPlatform(item.getPlatform());
        return list;
    }

    @Transform(from = PositionalQueuesList.class, to = PositionalQueues.class)
    public PositionalQueues transform(final PositionalQueuesList item, final PipelineContext context) {
        final PositionalQueues list = new PositionalQueues(item.size());
        list.addAll(item);
        list.setPlatform(item.getPlatform());
        return list;
    }

    @Transform(from = Series.class, to = MiniSeries.class)
    public MiniSeries transform(final Series item, final PipelineContext context) {
        final MiniSeries series = new MiniSeries();
        series.setLosses(item.getLosses());
        series.setProgress(item.getProgress());
        series.setTarget(item.getWinsRequired());
        series.setWins(item.getWins());
        return series;
    }

    @Transform(from = SummonerPositions.class, to = LeaguePositions.class)
    public LeaguePositions transform(final SummonerPositions item, final PipelineContext context) {
        final LeaguePositions positions = new LeaguePositions(item.size());
        for(final com.merakianalytics.orianna.types.dto.league.LeaguePosition position : item) {
            positions.add(transform(position, context));
        }
        positions.setPlatform(item.getPlatform());
        positions.setSummonerId(item.getSummonerId());
        return positions;
    }
}
